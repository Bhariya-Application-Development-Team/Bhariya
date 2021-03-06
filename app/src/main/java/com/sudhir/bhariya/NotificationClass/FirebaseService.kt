package com.sudhir.bhariya.NotificationClass

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.esewa.android.sdk.payment.ESewaPayment
import com.google.android.gms.maps.model.LatLng


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sudhir.bhariya.*
import com.sudhir.bhariya.fragments.DriverFragment
import com.sudhir.bhariya.fragments.ProfileFragment
import org.json.JSONObject
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"
class FirebaseService : FirebaseMessagingService() {


    companion object{
        var sharedPref : SharedPreferences? =null

        var token : String?
        get() {
            return sharedPref?.getString("token", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("token", value)?.apply()
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        println("##################")
        println(message.data["selectedPlaceEvent"])
//        println(message.data["latitude"] + "Latitude")


        println("######### Selected Place Event #########")
        println(message.data["selectedPlaceEvent"])
        println("#######################Token##############")
        println(message.data)
        println("##############phonenumber################")
        println(message.data["phonenumber"])
        println(message.data["token"])


//        if(message.data["title"] == "Trip Request"){
//
//        }

        if (message.data["title"]=="Trip request") {
            val intent = Intent(this, DriverRideActivity::class.java)
            intent.putExtra("phonenumber", message.data["phonenumber"])
            intent.putExtra("selectedPlaceEvent", message.data["selectedPlaceEvent"])
            intent.putExtra("token", message.data["token"])
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)

        } else if(message.data["title"] == "Ride Confirmation."){

            val intent = Intent(this, UserRideBegin::class.java)
            intent.putExtra("phonenumber", message.data["phonenumber"])
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)

        }
        else if(message.data["title"] == "Ride Completed!"){

            val intent = Intent(this, UserRideBegin::class.java)
            intent.putExtra("phonenumber", message.data["phonenumber"])
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)

        }
        else {

            val intent = Intent(this, DriverRideActivity::class.java)
            intent.putExtra("selectedPlaceEvent", message.data["selectedPlaceEvent"])
            intent.putExtra("token", message.data["token"])
//        intent.putExtra("distance",message.data["distance"])
//        intent.putExtra("total_fare",message.data["total_fare"])
//        intent.putExtra("startPoint",message.data["startPoint"])
//        intent.putExtra("endPoint",message.data["endPoint"])


            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply{
            description = "My channel Description"
            enableLights(true)
            lightColor = Color.BLUE
        }
        notificationManager.createNotificationChannel(channel)
    }


}