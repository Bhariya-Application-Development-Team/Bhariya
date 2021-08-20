package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.Response.TripResponse
import com.sudhir.bhariya.Response.UserTripResponse
import retrofit2.Response
import retrofit2.http.*

interface TripAPI {

    @FormUrlEncoded
    @POST("saveTrip")
    suspend fun saveTrip(
        @Field("Source") Source :String,
        @Field("Destination") Destination :String,
        @Field("Cost") Cost :String,
        @Field("Date") Date :String,
        @Field("Status") Status :String,
        @Field("DriverId") DriverId :String,
        @Field("Vehicle") Vehicle :String,
        @Field("ReferenceId") ReferenceId :String,
        ): Response<TripResponse>

    @GET("trip/show")
    suspend fun  getAllTrips(): Response<UserTripResponse>
}