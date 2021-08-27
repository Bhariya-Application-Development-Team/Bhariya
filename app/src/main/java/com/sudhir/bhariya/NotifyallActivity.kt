package com.sudhir.bhariya

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.firebase.ui.auth.util.data.AuthOperationManager.getInstance
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.installations.FirebaseInstallations

import com.google.firebase.messaging.FirebaseMessaging

import com.google.gson.Gson
import com.sudhir.bhariya.NotificationClass.FirebaseService
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
    private lateinit var openActivity : EditText
    lateinit var etToken : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifyall)
//        FirebaseInstanceIdReceiver.getInstance()
        etToken = findViewById(R.id.ettoken)
        openActivity = findViewById(R.id.openActivity)
//        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
//            etToken.setText(it.token)
//        }
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
         val firebaseToken = it.result!!.token
            FirebaseService.token = it.result!!.token
            etToken.setText(firebaseToken)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        btnPublish = findViewById(R.id.btnPublish)
        name = findViewById(R.id.name)
        texts = findViewById(R.id.texts)
        btnPublish.setOnClickListener{
            val title = name.text.toString()
            val message = texts.text.toString()
            val recipientToken = etToken.text.toString()
            val click_action = openActivity.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty() ){
                PushNotification(
                    NotificationData(title, message, click_action),
                    TOPIC,

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