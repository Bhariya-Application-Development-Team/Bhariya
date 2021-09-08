package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.DriverLoginResponse
import com.sudhir.bhariya.Response.DriverResponse
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.Response.UserResponse
import com.sudhir.bhariya.entity.Driver
import com.sudhir.bhariya.entity.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface  DriverAPI {


    @POST("/driver/register")
    suspend fun signupDriver(
        @Body driver: Driver
    ): Response<DriverLoginResponse>

    @FormUrlEncoded
    @POST("/driver/retrieve")
    suspend fun retrieveDriver(
        @Field("Phonenumber") phonenumber: String
    ) : Response<DriverResponse>

    @GET("driver/single/")
    suspend fun viewDriver(
        @Header("Authorization") token: String,
    ):Response<DriverResponse>

    @FormUrlEncoded
    @POST("driver/login/")
    suspend fun checkDriver(
        @Field("Phonenumber") phonenumber :String,
        @Field("password") password :String,

        ):Response<DriverLoginResponse>


    @Multipart
    @PUT("/driver/registration/citizenship")
    suspend fun imageOneUpload(
        @Part("Phonenumber") phonenumber: String,
        @Part file: MultipartBody.Part
    ): Response<DriverResponse>

    @Multipart
    @PUT("driver/registration/license")
    suspend fun imageTwoUpload(
        @Part("Phonenumber") phonenumber: String,
        @Part file: MultipartBody.Part
    ): Response<DriverResponse>

    @Multipart
    @PUT("driver/registration/bluebook")
    suspend fun imageThreeUplaod(
        @Part("Phonenumber") phonenumber: String,
        @Part file: MultipartBody.Part
    ): Response<DriverResponse>



}