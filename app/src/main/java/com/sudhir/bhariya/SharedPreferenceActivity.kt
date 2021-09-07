package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class SharedPreferenceActivity : AppCompatActivity() {

    var phonenumber = ""
    var password = ""
    var fullname = ""
    var token = ""
    var firebaseToken =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        val intent = intent
        phonenumber = intent.getStringExtra("phonenumber").toString()
        fullname = intent.getStringExtra("fullname").toString()
        password = intent.getStringExtra("password").toString()
        firebaseToken = intent.getStringExtra("firebaseToken").toString()

        save()
        get()


    }
    private fun save(){
        val sharedPreference = getSharedPreferences("MyPreference", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("phonenumber",phonenumber)
        editor.putString("password",password)
        editor.putString("fullname",fullname)
        editor.putString("token",token)
        editor.putString("firebaseToken", firebaseToken)
        editor.apply()
        Toast.makeText(this@SharedPreferenceActivity, "Saved Data!", Toast.LENGTH_SHORT).show()
    }
    private fun get(){
        val sharedPreference = getSharedPreferences("MyPreference", MODE_PRIVATE)
        fullname = sharedPreference.getString("fullname","").toString()
        phonenumber = sharedPreference.getString("phonenumber", "").toString()
        password = sharedPreference.getString("password", "").toString()
        token = sharedPreference.getString("token","").toString()
        firebaseToken = sharedPreference.getString("firebaseToken", "").toString()
        Toast.makeText(this, "LOGIN SUCESSFUL", Toast.LENGTH_SHORT).show()

        startActivity(
            Intent(
                this@SharedPreferenceActivity, MainActivity::class.java
            )
        )

    }
}