package com.sudhir.bhariya

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.firebase.ui.auth.util.data.AuthOperationManager.getInstance
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.installations.FirebaseInstallations

import com.google.firebase.messaging.FirebaseMessaging

import com.google.gson.Gson
import com.sudhir.bhariya.NotificationClass.FirebaseService
import com.sudhir.bhariya.Remote.RetrofitClient.getInstance
import com.sudhir.bhariya.entity.User

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


const val  TOPIC = "/topics/myTopic"
class NotifyallActivity : AppCompatActivity() {

    val TAG = "NotifyallActivity"
    private lateinit var btnPublish : Button
    private lateinit var name : EditText
    private lateinit var texts : EditText
    private lateinit var rdouser : RadioButton
    private lateinit var database : FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var etToken : EditText
    var listtoken = ArrayList<String>()
    var title = " "
    var message = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifyall)
        etToken = findViewById(R.id.ettoken)
        rdouser = findViewById(R.id.userrdo)
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users")
        name = findViewById(R.id.name)
        texts = findViewById(R.id.texts)

//            val recipientToken = etToken.text.toString()
//        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
//            etToken.setText(it.token)
//        }
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
//         val firebaseToken = it.result!!.token
//            FirebaseService.token = it.result!!.token
//            etToken.setText(firebaseToken)
//        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
                val  firebaseToken = it.result
                // DO your thing with your firebase token
//                etToken.setText(firebaseToken)
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        btnPublish = findViewById(R.id.btnPublish)

        btnPublish.setOnClickListener{
            Log.e("hellooooooooooooo token", listtoken.toString())
            title = name.text.toString()
            message = texts.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty() ){
                for(i in listtoken){
                    println("############")
                    println(i)
                    PushNotification(
                        NotificationData(title, message),
                        i

                    ).also {
                        sendNotification(it)
                    }
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

    private fun getData(){
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<User>()

                for (data in snapshot.children){
                    var model = data.getValue(User::class.java)
                    list.add(model as User)
                    var tllist = model.Token
                    listtoken.add(tllist.toString())

                }
                println("#########################")

                Log.e("Token are", listtoken.toString())
                Log.e("Users are: ", list.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }

        })
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.userrdo ->
                    if (checked) {
                        getData()
                    }

            }
        }
    }
}