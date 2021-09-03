package com.sudhir.bhariya

import com.sudhir.bhariya.entity.EventBus.SelectedPlaceEvent

data class NotificationData (
    private var selectedPlaceEvent : SelectedPlaceEvent?=null,
//    val distance : String? = null,
//    val total_fare : String? = null,
//    val startPoint : String? = null,
//    val endPoint : String? = null,
    val title: String,
    val message : String,
        )