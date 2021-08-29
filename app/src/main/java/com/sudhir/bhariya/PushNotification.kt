package com.sudhir.bhariya

data class PushNotification (
    val latitude : String,
    val longitude : String,
    val data: NotificationData,
    val to : String,

        )