package com.example.mmp

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

object OkHttpHelper {

    private val client = OkHttpClient()

    // POST request method
    fun post(url: String, params: Map<String, String>): String? {
        // Create the form body from the parameters
        val formBody: RequestBody = FormBody.Builder().apply {
            params.forEach { (key, value) ->
                add(key, value)
            }
        }.build()

        // Create the request with the URL and form body
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        return try {
            // Execute the request and get the response
            val response: Response = client.newCall(request).execute()
            response.body?.string() // Return the response body as a string
        } catch (e: IOException) {
            e.printStackTrace()
            null // Return null if an exception occurs
        }
    }
}
