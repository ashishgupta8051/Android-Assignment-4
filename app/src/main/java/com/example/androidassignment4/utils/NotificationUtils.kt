package com.example.androidassignment4.utils

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi


object NotificationUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(context: Context, title: String?, body: String?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel_id"
        val channelName = "Channel Name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val builder: Notification.Builder = Notification.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
        notificationManager.notify(0, builder.build())
    }
}
