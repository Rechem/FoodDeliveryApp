package com.example.fooddelieveryapp

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context;
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.content.edit

class FirebaseService : FirebaseMessagingService() {

    private val TAG = "firebase_service"
    lateinit var prefs: SharedPreferences
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body

        Log.d(TAG, "recived title: $title")
        Log.d(TAG, "recived title: $body")

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
            notificationManager.createNotificationChannel(
                channel
            )
        }

        notificationManager.notify(0, builder.build())

        super.onMessageReceived(remoteMessage)
    }
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        prefs = getSharedPreferences("firebase", Context.MODE_PRIVATE)
        prefs.apply {
                this.edit {
                    putString("fcmToken", token)
                }
        }
    }
}