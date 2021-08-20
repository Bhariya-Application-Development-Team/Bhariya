package com.sudhir.bhariya.Repository

import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.TripResponse
import com.sudhir.bhariya.Response.UserTripResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.api.TripAPI
import com.sudhir.bhariya.entity.Trip
import retrofit2.Response
import retrofit2.http.GET

class TripRepository : MyApiRequest() {

    private val tripAPI =
        ServiceBuilder.buildService(TripAPI::class.java)

    suspend fun getAllTrips(): UserTripResponse {
        return apiRequest {
            tripAPI.getAllTrips()
        }
    }

    suspend fun saveTrip(Source : String, Destination : String, Cost: String, Status: String, Date : String,
    ReferenceId : String, DriverId : String, Vehicle : String): TripResponse {
        return apiRequest {
            tripAPI.saveTrip(Source = Source, Destination = Destination, Cost =Cost, Status= Status, Date = Date, ReferenceId = ReferenceId, DriverId = DriverId, Vehicle = Vehicle)
        }
    }


}