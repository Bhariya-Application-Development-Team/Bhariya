package com.sudhir.bhariya.entity

data class ActiveDriver(
    var Phonenumber : String? = null,
    var Fullname: String? = null,
    var Address: String? = null,
    var password: String? = null,
    var Longitude: String? = null,
    var Latitude: String? = null,
    var Token : String? = null,
    var Role : String? = "Driver"
)
