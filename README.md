Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency
	dependencies {
	        implementation 'com.github.Appmontize:mmpsdk:Tag'
	}




Usage
Initialize the SDK
Initialize the SDK in your app's main Activity:

MmpSdk.initialize(this)



Track Events
Use the SDK to track user events:

MmpSdk.registeredNow(context, "click_id", "tid")
MmpSdk.subscribed(context, "click_id", "tid")
MmpSdk.completed(context, "click_id", "tid")
