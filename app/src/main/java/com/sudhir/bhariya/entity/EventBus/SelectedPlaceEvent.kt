package com.sudhir.bhariya.entity.EventBus

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.lang.StringBuilder

class SelectedPlaceEvent(var origin:LatLng, var destination : LatLng) : Serializable {

    val originString : String
    get() = StringBuilder()
        .append(origin.latitude)
        .append(",")
        .append(origin.longitude)
        .toString()

    val destinationString : String
        get() = StringBuilder()
            .append(destination.latitude)
            .append(",")
            .append(destination.longitude)
            .toString()

}