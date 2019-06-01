package com.ecolec.cliente.fcm

import android.util.Log
import androidx.core.app.NotificationCompat
import com.ecolec.cliente.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.content.Context
import android.app.NotificationChannel
import android.os.Build


class MessageService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d("TAG", "Message data payload: " + remoteMessage.data)

            var builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Ecolec")
                .setContentText("Un reciclador esta en camino")
                .setPriority(NotificationCompat.PRIORITY_HIGH).build()

            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel("1", "Ecolec", NotificationManager.IMPORTANCE_HIGH)
                mNotificationManager.createNotificationChannel(mChannel)
                mNotificationManager.notify(1, builder)
            }
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d("TAG", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String?) {
        Log.d("TAG", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token)
    }
}