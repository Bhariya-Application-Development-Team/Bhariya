package com.sudhir.bhariya.entity.EventBus

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.lang.StringBuilder

class SelectedPlaceEvent(var origin: LatLng, var destination: LatLng) : Parcelable{

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

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(LatLng::class.java.classLoader)!!,
        parcel.readParcelable(LatLng::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(origin, flags)
        parcel.writeParcelable(destination, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectedPlaceEvent> {
        override fun createFromParcel(parcel: Parcel): SelectedPlaceEvent {
            return SelectedPlaceEvent(parcel)
        }

        override fun newArray(size: Int): Array<SelectedPlaceEvent?> {
            return arrayOfNulls(size)
        }
    }

}