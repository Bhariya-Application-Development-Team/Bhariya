package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit



class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etPhonenumber: EditText
    private lateinit var btnSendcode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_forgot_password)
        etPhonenumber = findViewById(R.id.etPhonenumber)
        btnSendcode = findViewById(R.id.btnSendcode)

        btnSendcode.setOnClickListener {
            if (TextUtils.isEmpty(etPhonenumber.text)) {
                Toast.makeText(this, "Please Enter the Phone Number", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CodeVerificationActivity::class.java)
                intent.putExtra("phonenumber",etPhonenumber.text.toString())
                startActivity(intent)
            }
        }
    }
}