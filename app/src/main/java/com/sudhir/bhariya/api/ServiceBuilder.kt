package com.sudhir.bhariya

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL =
//         For Running on Emulator
//    "http://10.0.2.2:3000/"

        "http://bhariya.herokuapp.com/"
        // For Running on a real Device
//        "http://192.168.0.124:3000/"

    var token: String? = null
    private  val okHttp = OkHttpClient.Builder()
    private  val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    //Create retrofit instance
    private val retrofit = retrofitBuilder.build()
    //Generic function
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/"
    }

    fun loadprofilePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/"
    }
}
