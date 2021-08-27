package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class SharedPreferenceActivity : AppCompatActivity() {

    var phonenumber = ""
    var password = ""
    var username = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        val intent = intent
        phonenumber = intent.getStringExtra("phonenumber").toString()
        username = intent.getStringExtra("fullname").toString()
        password = intent.getStringExtra("password").toString()

        save()
        get()


    }
    private fun save(){
        val sharedPreference = getSharedPreferences("MyPreference", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("phonenumber",phonenumber)
        editor.putString("password",password)
        editor.apply()
        Toast.makeText(this@SharedPreferenceActivity, "Saved Data!", Toast.LENGTH_SHORT).show()
    }
    private fun get(){
        val sharedPreference = getSharedPreferences("MyPreference", MODE_PRIVATE)
        phonenumber = sharedPreference.getString("phonenumber", "").toString()
        password = sharedPreference.getString("password", "").toString()
        Toast.makeText(this, "LOGIN SUCESSFUL", Toast.LENGTH_SHORT).show()

        startActivity(
            Intent(
                this@SharedPreferenceActivity, MainActivity::class.java
            )
        )

    }
}