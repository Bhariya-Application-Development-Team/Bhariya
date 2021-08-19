package com.sudhir.bhariya.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feedback(
    var Username : String? = null,
    var Feedback: String? = null,
){
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}
