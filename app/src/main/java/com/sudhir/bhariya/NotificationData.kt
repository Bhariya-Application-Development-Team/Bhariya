package com.sudhir.bhariya

import com.sudhir.bhariya.entity.EventBus.SelectedPlaceEvent

data class NotificationData (
    private var selectedPlaceEvent : SelectedPlaceEvent?=null,
    val title: String,
    val message : String,
        )