package com.sudhir.bhariya.Repository

import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.DriverResponse
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.UserAPI
import com.sudhir.bhariya.api.DriverAPI
import com.sudhir.bhariya.entity.User
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class DriverRepository : MyApiRequest() {

    private val driverApi =
        ServiceBuilder.buildService(DriverAPI::class.java)

    suspend fun imageOneUpload(phonenumber : String, body : MultipartBody.Part) : DriverResponse{
        return apiRequest {
            driverApi.imageOneUpload(phonenumber, body)
        }
    }

    suspend fun imageTwoUpload(phonenumber: String, body: MultipartBody.Part) : DriverResponse{
        return apiRequest {
            driverApi.imageTwoUpload(phonenumber, body)
        }
    }

    suspend fun imageThreeUpload(phonenumber: String, body: MultipartBody.Part) : DriverResponse{
        return apiRequest {
            driverApi.imageThreeUplaod(phonenumber, body)
        }
    }

}