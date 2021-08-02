package com.sudhir.bhariya.api

import android.widget.TextView
import java.util.*

object Common {
    fun setWelcomeMessage(txtWelcome: TextView) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if(hour >=1 && hour <= 12)
            txtWelcome.setText(java.lang.StringBuilder("Good Morning"))
        else if (hour >12 && hour <= 17)
            txtWelcome.setText(java.lang.StringBuilder("Good Afternoon"))
        else
            txtWelcome.setText(java.lang.StringBuilder("Good Evening"))
    }

    val DRIVERS_LOCATION_REFERENCE: String = "DriversLocation"

}