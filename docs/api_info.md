# API Information

## AdiscopeGlobalSdk
### Initialize
이니셜라이즈 함수는 아래 두가지로 지원하며, 용도에 따라 선택해서 사용할 수 있다.
* `AdiscopeGlobalSdk.initialize(Activity activity, int mediaId, String mediaSecret, AdiscopeGlobalInitializationListener listener)`
    * 빌드 환경에 따라 매체 환경을 코드 내에서 분기처리하여 이니셜라이즈하는 경우
* `AdiscopeGlobalSdk.initialize(Activity activity, AdiscopeGlobalInitializationListener listener)`
    * mediaId, mediaSecret 값을 고정으로 사용하는 경우

<br></br>

#### A. `void initialize(Activity activity, int mediaId, String mediaSecret, AdiscopeGlobalInitializationListener listener)`

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

#### B. `void initialize(Activity activity, AdiscopeGlobalInitializationListener listener)`
앱 프로젝트의 AndroidManifest.xml에 `adiscope.global.mediaId`, `adiscope.global.mediaSecret` meta-data를 설정하고
`AdiscopeGlobalSdk.initialize(activity, listener)` 함수를 사용하면 SDK가 해당 meta-data 값을 읽어와 이니셜라이즈를 수행한다.

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

### isInitialized
이니셜라이즈 여부를 체크하기 위한 함수로 이니셜라이즈가 되어있는 상태면 true, 그렇지 않으면 false를 반환한다.
- `boolean isInitialized()`

**Java**
```java
Log.d(TAG, "AdiscopeGlobalSdk.isInitialized = " + AdiscopeGlobalSdk.isInitialized());
```

**Kotlin**
```kotlin
Log.d(TAG, "AdiscopeGlobalSdk.isInitialized = ${AdiscopeGlobalSdk.isInitialized()}")
```

<br></br>

### Set User Id
퍼블리셔 앱을 사용하는 유저의 아이디를 설정하기 위한 함수. 사용자를 구분하여 보상을 지급하기 위해 사용된다.  
오퍼월 진입 전 반드시 설정해야 한다.
- `boolean setUserId(String userId)`

**Java**
```java
// set user id to identify the user in reward information
AdiscopeGlobalSdk.setUserId(userId);
```

**Kotlin**
```kotlin
// set user id to identify the user in reward information
AdiscopeGlobalSdk.setUserId(userId)
```

<br></br>

### Get Sdk Version
Global Adiscope SDK(`adiscope-sdk`) 버전을 반환한다.
* `String getSdkVersion()`

**Java**
```java
Log.d(TAG, "AdiscopeGlobalSdk.getSdkVersion = " + AdiscopeGlobalSdk.getSdkVersion());
```

**Kotlin**
```kotlin
Log.d(TAG, "AdiscopeGlobalSdk.getSdkVersion = ${AdiscopeGlobalSdk.getSdkVersion()}")
```

<br></br>

### Get Network Adapter Version
연동되어 있는 네트워크 어댑터의 버전을 반환한다.
* `String getNetworkVersion()`

**Java**
```java
Log.d(TAG, "AdiscopeGlobalSdk.getNetworkVersion = " + AdiscopeGlobalSdk.getNetworkVersion());
```

**Kotlin**
```kotlin
Log.d(TAG, "AdiscopeGlobalSdk.getNetworkVersion = ${AdiscopeGlobalSdk.getNetworkVersion()}")
```

<br></br>

### Get Network Sdk Version
연동되어 있는 네트워크 SDK 버전을 반환한다.
* `String getNetworkSdkVersion()`

**Java**
```java
Log.d(TAG, "AdiscopeGlobalSdk.getNetworkSdkVersion = " + AdiscopeGlobalSdk.getNetworkSdkVersion());
```

**Kotlin**
```kotlin
Log.d(TAG, "AdiscopeGlobalSdk.getNetworkSdkVersion = ${AdiscopeGlobalSdk.getNetworkSdkVersion()}")
```

<br></br>

## AdiscopeGlobalOfferwall
### Show
오퍼월 광고를 사용자에게 표시한다.  
오퍼월에 진입하기 전에 반드시 유저 아이디를 설정해야 하며, 설정하지 않았을 경우 오퍼월 진입이 불가하다.
* `void show(String unitId, Activity activity, OfferwallListener listener)`
    * activity: 상위 액티비티
    * unitId: 사용자에게 표시할 오퍼월 광고의 유닛 아이디
    * listener: 오퍼월의 show 여부에 대한 결과를 받기 위한 콜백 리스너
        * `onOfferwallOpened(unitId)`: 오퍼월 광고가 열릴 때
        * `onOfferwallFailedToOpened(unitId, error)`: 오퍼월 광고를 보여줄 수 없을 때

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
