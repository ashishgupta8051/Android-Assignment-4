package com.example.androidassignment4.firebasemessaging

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.androidassignment4.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Message data payload: " + remoteMessage.data)
            if (remoteMessage.notification != null) {
                val title = remoteMessage.notification!!.title
                val body = remoteMessage.notification!!.body
                NotificationUtils.showNotification(applicationContext, title, body)
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, " TOKEN2: $token")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
