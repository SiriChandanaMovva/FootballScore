package uk.ac.tees.w9623063.myapplication

import android.app.Application
import com.onesignal.OneSignal

// Replace the below with your own ONESIGNAL_APP_ID
const val ONESIGNAL_APP_ID = "e9749e32-3373-4f4a-92f2-029d79939d6e"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}