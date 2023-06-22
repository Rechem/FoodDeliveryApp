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
import androidx.core.app.NotificationCompat

class FirebaseService : FirebaseMessagingService() {

    private val TAG = "firebase_service"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body

        Log.d(TAG, "recived title: $title")
        Log.d(TAG, "recived title: $body")

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
//                .setSmallIcon(R.drawable.alert_light_frame)
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

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        // sendRegistrationToServer(token)
    }
}