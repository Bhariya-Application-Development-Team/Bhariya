package com.sudhir.bhariya


import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.entity.User
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("/register/")
    suspend fun signupUser(
        @Body user: User
    ): Response<LoginResponse>

//    @GET("user/single/")
//    suspend fun viewUser(
//        @Header("Authorization") token: String,
//    ):Response<UserResponse>

    @FormUrlEncoded
    @POST("user/login/")
    suspend fun checkUser(
        @Field("username") username :String,
        @Field("password") password :String,

    ):Response<LoginResponse>

    //for testing ony
    @FormUrlEncoded
    @POST("/user/login/")
    suspend fun checkUsertest(
        @Field("username") username :String,
        @Field("password") password :String,

        ):Response<LoginResponse>

}