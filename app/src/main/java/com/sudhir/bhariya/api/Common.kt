package com.sudhir.bhariya.api

import android.animation.ValueAnimator
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
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



    fun formatDuration(duration: String): CharSequence? {
        if(duration.contains("mins"))
            return duration.substring(0,duration.length-1)
        else
            return duration
    }

    fun formatAddress(startAddress: String): CharSequence? {

        val firstIndexComma = startAddress.indexOf(",")
        return startAddress.substring(0,firstIndexComma)

    }

    fun valueAnimate(duration: Int, listener: ValueAnimator.AnimatorUpdateListener): ValueAnimator?{
        val va = ValueAnimator.ofFloat(0f, 100f)
        va.duration = duration.toLong()
        va.addUpdateListener(listener)
        va.repeatCount = ValueAnimator.INFINITE
        va.repeatMode = ValueAnimator.RESTART
        va.start()
        return va
    }


    val DRIVERS_LOCATION_REFERENCE: String = "DriversLocation"

}