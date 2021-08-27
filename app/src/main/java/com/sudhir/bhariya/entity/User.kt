package com.sudhir.bhariya.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var Phonenumber : String? = null,
    var Fullname: String? = null,
    var Address: String? = null,
    var password: String? = null,
    var Token : String?= null,
    var Role : String? = "User"

){
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}