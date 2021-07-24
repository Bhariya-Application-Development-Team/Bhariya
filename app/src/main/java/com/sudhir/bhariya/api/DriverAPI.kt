package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.DriverResponse
import com.sudhir.bhariya.Response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface DriverAPI {

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