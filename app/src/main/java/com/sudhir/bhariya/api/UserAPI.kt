package com.sudhir.bhariya


import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.Response.UserResponse
import com.sudhir.bhariya.entity.User
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("/register/")
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

    @FormUrlEncoded
    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("id") id : String,
        @Field("Fullname") fullname : String,
        @Field("Address") address : String,
        @Field("Phonenumber") phonenumber: String
    ): Response<LoginResponse>

}