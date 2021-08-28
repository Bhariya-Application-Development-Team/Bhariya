package com.sudhir.bhariya.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feedback(
    var feedback: String? = null,
    var username : String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(feedback)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feedback> {
        override fun createFromParcel(parcel: Parcel): Feedback {
            return Feedback(parcel)
        }

        override fun newArray(size: Int): Array<Feedback?> {
            return arrayOfNulls(size)
        }
    }

}
//{
//    @PrimaryKey(autoGenerate = true)
//    var userId : Int = 0
//}
