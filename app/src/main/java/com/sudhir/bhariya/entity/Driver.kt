package com.sudhir.bhariya.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Driver(
    var Phonenumber : String? = null,
    var Fullname: String? = null,
    var Longitude: String? = null,
    var Latitude: String? = null,
    var Token : String? = null
){

    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}