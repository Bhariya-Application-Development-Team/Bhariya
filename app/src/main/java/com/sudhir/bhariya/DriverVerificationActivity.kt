package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DriverVerificationActivity : AppCompatActivity() {
    private lateinit var btnHome : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_verification)

        btnHome = findViewById(R.id.btnHome)

        btnHome.setOnClickListener{

            val intent = Intent(this@DriverVerificationActivity, MainActivity::class.java)
            startActivity(intent)

        }

    }
}