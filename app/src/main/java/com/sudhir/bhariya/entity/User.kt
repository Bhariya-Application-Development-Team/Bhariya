package com.sudhir.bhariya.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var fname : String? = null,
    var username: String? = null,
    var password: String? = null,
    var confirmpassword: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}