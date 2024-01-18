# Gofferwall-Android-Sample

## Requirements

* minSdkVersion 21
* compileSdkVersion 33
* used Java 8
* The Core SDK includes gms library for collecting ADID  
  (`com.google.android.gms:play-services-ads-identifier:18.1.0`)

<br>

## Contents
* [Integration Guide](#Integration-Guide)
* [Other API](./docs/api_info.md)
* [Error Code](./docs/error_info.md)
* [Reward Callback](./docs/reward_callback.md)

</br>

## Integration Guide

### 1. Add the Sdk to your project

**build.gradle(root)**

```groovy
allprojects {
    repositories {
        // [required] global adiscope library
        maven {
            url 'https://repository.adiscope.com/repository/adiscope-global/'
        }
      
        // must add it to use the gofferwall feature 
        // [required] tapjoy adapter
        maven { url "https://sdk.tapjoy.com/" }
        // [required] tnk adapter
        maven { url "https://repository.tnkad.net:8443/repository/public/" }
    }
}
```

**build.gradle(app)**

```groovy
dependencies {
    // [required] global adiscope core library
    implementation "com.adiscope.global:adiscope-sdk:1.0.0"

    // must add it to use the gofferwall feature 
    // [required] tapjoy adapter
    implementation "com.adiscope.global:adapter-tapjoy:13.2.1.0"
    // [required] tnk adapter
    implementation "com.adiscope.global:adapter-tnk:8.03.08.0"
}
```
<br></br>

### 2. Set AndroidManifest.xml
Set your TNK AppId received from Adiscope. TNK Sdk reads this information and initializes it.
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:tools="http://schemas.android.com/tools">
    <application
        ...
    
        <!-- [required] for tnk adapter -->
        <meta-data
            android:name="tnkad_app_id"
            android:value="INPUT_YOUR_TNK_APP_ID" />
    </application>
</manifest>
```

<br></br>

### 3. Initialize Global Adiscope

Sdk provide two Initialize functions below, and you can be used one of them depending on the purpose.
* `AdiscopeGlobalSdk.initialize(Activity activity, int mediaId, String mediaSecret, AdiscopeGlobalInitializationListener listener)`
  * If the media environment needs to be changed depending on the build environment programmatically
* `AdiscopeGlobalSdk.initialize(Activity activity, AdiscopeGlobalInitializationListener listener)`
  * If you use mediaId and secretKey as fixed

<br></br>

#### A) AdiscopeGlobalSdk.initialize(activity, mediaId, mediaSecret, listener)

**Java**
```java
AdiscopeGlobalSdk.initialize(activity,
        INPUT_YOUR_MEDIA_ID,
        "INPUT_YOUR_MEDIA_SECRET_KEY",
        new AdiscopeGlobalInitializationListener(){
            @Override
            public void onInitialized(boolean isSuccess){
                if (isSuccess) {
                    Log.d(TAG, "AdiscopeGlobalSdk initialized.");
                } else {
                    Log.d(TAG, "AdiscopeGlobalSdk initialize failed.");
                }  
        }
        });
```

**Kotlin**
```kotlin
AdiscopeGlobalSdk.initialize(
    activity,
    INPUT_YOUR_MEDIA_ID,
    "INPUT_YOUR_MEDIA_SECRET_KEY"
) { isSuccess ->
    if (isSuccess) {
        Log.d(TAG, "AdiscopeGlobalSdk initialized.")
    } else {
        Log.d(TAG, "AdiscopeGlobalSdk initialize failed.")
    }
}
```

<br></br>

#### B) AdiscopeGlobalSdk.initialize(activity, listener)
If you have defined `adiscope.global.mediaId`, `adiscope.global.mediaSecret` metadata in your AndroidManifest.xml, you can use function `AdiscopeGlobalSdk.initialize(activity, listener)`.  
Sdk reads `adiscope.global.mediaId`, `adiscope.global.mediaSecret` meta-data and initializes it.


**AndroidManifest.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <application
        ...

        <!-- Define mediaId, secretKey metadata and 
             use AdiscopeGlobalSdk.initialize(activity, listener) function,
             sdk reads this information and initializes it  -->
        <meta-data
            android:name="adiscope.global.mediaId"
            android:value="INPUT_YOUR_MEDIA_ID" />
        <meta-data
            android:name="adiscope.global.mediaSecret"
            android:value="INPUT_YOUR_MEDIA_SECRET" />
    </application>

</manifest>
```

**Java**
```java
AdiscopeGlobalSdk.initialize(activity,
        new AdiscopeGlobalInitializationListener(){
            @Override
            public void onInitialized(boolean isSuccess){
                if (isSuccess) {
                    Log.d(TAG, "AdiscopeGlobalSdk initialized.");
                } else {
                    Log.d(TAG, "AdiscopeGlobalSdk initialize failed.");
                }  
        }
        });
```

**Kotlin**
```kotlin
AdiscopeGlobalSdk.initialize(
    activity
) { isSuccess ->
    if (isSuccess) {
        Log.d(TAG, "AdiscopeGlobalSdk initialized.")
    } else {
        Log.d(TAG, "AdiscopeGlobalSdk initialize failed.")
    }
}
```

<br></br>
### 4. Show Offerwall

**Java**
```java
// you should be set userId before call show offerwall
AdiscopeGlobalSdk.setUserId(userId);
        
AdiscopeGlobalOfferwall.show(
        "INPUT_YOUR_GOFFERWALL_UNIT_ID",
        activity,
        new OfferwallListener() {
            @Override
            public void onOfferwallOpened(String unitId) {
                Log.d(TAG, "onOfferwallOpened: " + unitId);
            }

            @Override
            public void onOfferwallFailedToOpen(String unitId, AdiscopeGlobalError error) {
                Log.d(TAG, "onOfferwallFailedToOpen: " + unitId + " - " + error);
            }
        });
```


**Kotlin**
```kotlin

// you should be set userId before call show offerwall
AdiscopeGlobalSdk.setUserId(userId)

AdiscopeGlobalOfferwall.show("INPUT_YOUR_GOFFERWALL_UNIT_ID", activity, object : OfferwallListener {
    override fun onOfferwallOpened(unitId: String?) {
        Log.d(TAG, "AdiscopeGlobalOfferwall.onOfferwallOpened: $unitId")
    }

    override fun onOfferwallFailedToOpen(unitId: String?, error: AdiscopeGlobalError?) {
        Log.d(TAG, "AdiscopeGlobalOfferwall.onOfferwallFailedToOpen: $unitId - $error")
    }

})
```