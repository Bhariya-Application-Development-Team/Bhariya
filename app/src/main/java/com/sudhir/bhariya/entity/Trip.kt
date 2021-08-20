package com.sudhir.bhariya.entity

import androidx.room.PrimaryKey

data class Trip (
    var Source : String? = null,
    var Destination : String? = null,
    var Cost : String? =null,
    var Status : String?= null,
    var ReferenceId : String? =null,
    var Vehicle : String? =null,
    var Date : String? = null,
    var DriverId : String? =null,
        )