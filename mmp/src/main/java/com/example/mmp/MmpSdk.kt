package com.example.mmp
import android.content.Context
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails


object MmpSdk {
    private const val TAG = "MmpSdk"

    fun initialize(context: Context) {
        retrieveInstallReferrer(context)
    }

    private fun retrieveInstallReferrer(context: Context) {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()

        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                    val response: ReferrerDetails = referrerClient.installReferrer
                    val referrerUrl = response.installReferrer

                    val params = referrerUrl.split("&").associate {
                        val (key, value) = it.split("=")
                        key to value
                    }

                    val clickId = params["referrer"]?.split(",")?.getOrNull(0)
                    val tid = params["referrer"]?.split(",")?.getOrNull(1)

                    Log.d(TAG, "Click ID: $clickId, TID: $tid")

                    if (clickId != null && tid != null) {
                        sendInstallationData(context, clickId, tid)
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                Log.w(TAG, "Install Referrer Service Disconnected.")
            }
        })
    }

    private fun sendInstallationData(context: Context, clickId: String, tid: String) {
        val params = mapOf(
            "click_id" to clickId,
            "tid" to tid,
            "device_model" to android.os.Build.MODEL,
            "android_version" to android.os.Build.VERSION.RELEASE,
            "advertising_id" to "dummy_ad_id" // Replace with real advertising ID if applicable
        )
        OkHttpHelper.post("https://magnetcents.co.in/mmpsdk/store_install.php", params)
    }

    fun registeredNow(context: Context, clickId: String, tid: String) {
        trackEvent(context, "registered_now", clickId, tid)
    }

    fun subscribed(context: Context, clickId: String, tid: String) {
        trackEvent(context, "subscribed", clickId, tid)
    }

    fun completed(context: Context, clickId: String, tid: String) {
        trackEvent(context, "completed", clickId, tid)
    }

    private fun trackEvent(context: Context, eventType: String, clickId: String, tid: String) {
        val params = mapOf(
            "event_type" to eventType,
            "click_id" to clickId,
            "tid" to tid
        )
        OkHttpHelper.post("https://magnetcents.co.in/mmpsdk/store_event.php", params)
    }
}

