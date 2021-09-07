package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UserRideBegin : AppCompatActivity() {
    private lateinit var btn_cancel_ride : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ride_begin)

        btn_cancel_ride = findViewById(R.id.btn_cancel_ride)

        btn_cancel_ride.setOnClickListener {

        }
    }
}