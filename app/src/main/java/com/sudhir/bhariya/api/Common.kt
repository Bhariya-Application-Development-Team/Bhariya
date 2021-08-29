package com.sudhir.bhariya.api

import android.animation.ValueAnimator
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.sudhir.bhariya.entity.DriverGeoModel
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

object Common {

    val markerList: HashSet<Marker> = HashSet<Marker>()
    val DRIVER_INFO_REFERENCE: String = "DriverInfo"
    val DRIVERS_LOCATION_REFERENCE: String = "DriversLocation"
    val driversFound : HashSet<DriverGeoModel> = HashSet<DriverGeoModel>()
    //    val currentRider : RiderModel? = null
    val RIDER_INFO_REFERENCE : String = "Riders"

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



}