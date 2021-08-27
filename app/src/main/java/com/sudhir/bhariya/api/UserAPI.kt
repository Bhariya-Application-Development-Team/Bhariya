package com.sudhir.bhariya


import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.Response.UserResponse
import com.sudhir.bhariya.entity.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("register/")
    suspend fun signupUser(
        @Body user: User
    ): Response<LoginResponse>

    @GET("user/single/")
    suspend fun viewUser(
        @Header("Authorization") token: String,
    ):Response<UserResponse>

    @FormUrlEncoded
    @POST("user/login/")
    suspend fun checkUser(
        @Field("Phonenumber") Phonenumber :String,
        @Field("password") password :String,

    ):Response<LoginResponse>

    @FormUrlEncoded
    @PUT("user/password/reset")
    suspend fun resetPassword(
        @Field("phonenumber") phonenumber :String,
        @Field("password") password :String,
    ): Response<LoginResponse>

    @Multipart
    @PUT("user/update")
    suspend fun updateUser(
        @Part("id") id : String,
        @Part("Fullname") fullname : String,
        @Part("Address") address : String,
        @Part("Phonenumber") phonenumber: String,
        @Part file: MultipartBody.Part
    ): Response<LoginResponse>

    @FormUrlEncoded
    @PUT("user/updateText")
    suspend fun updateUserText(
        @Field("id") id : String,
        @Field("Fullname") fullname : String,
        @Field("Address") address : String,
        @Field("Phonenumber") phonenumber: String,
    ): Response<LoginResponse>




}