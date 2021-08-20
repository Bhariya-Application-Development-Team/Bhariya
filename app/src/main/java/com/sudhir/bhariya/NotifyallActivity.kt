package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.firebase.ui.auth.util.data.AuthOperationManager.getInstance
import com.google.firebase.iid.FirebaseInstanceIdReceiver

import com.google.firebase.messaging.FirebaseMessaging

import com.google.gson.Gson
import com.sudhir.bhariya.Remote.RetrofitClient.getInstance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


const val  TOPIC = "/topics/myTopic"
class NotifyallActivity : AppCompatActivity() {

    private lateinit var btnPublish : Button
    private lateinit var name : EditText
    private lateinit var texts : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifyall)
//        FirebaseInstanceIdReceiver.getInstance()
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        btnPublish = findViewById(R.id.btnPublish)
        name = findViewById(R.id.name)
        texts = findViewById(R.id.texts)
        btnPublish.setOnClickListener{
            val title = name.text.toString()
            val message = texts.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty()){
                PushNotification(
                    NotificationData(title, message),
                    TOPIC
                ).also {
                    sendNotification(it)
                }
            }
        }


    }

    private fun sendNotification(notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch {

        try{
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful){
                Log.e("mainSuccess", "Message: ${Gson().toJson(response)}")
            }
            else{
               Log.e("error", response.errorBody().toString())
            }
        } catch (e:Exception){
            Log.e("Main", e.toString())
        }
    }
}