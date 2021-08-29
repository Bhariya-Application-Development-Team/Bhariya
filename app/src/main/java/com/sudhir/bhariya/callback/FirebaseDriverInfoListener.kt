package com.sudhir.bhariya.callback

import com.sudhir.bhariya.entity.DriverGeoModel

interface FirebaseDriverInfoListener {

    fun onDriverInfoLoadSuccess(driverGeoModel: DriverGeoModel?)

}