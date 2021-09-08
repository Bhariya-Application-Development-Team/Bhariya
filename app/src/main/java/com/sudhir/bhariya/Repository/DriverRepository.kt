package com.sudhir.bhariya.Repository

import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.DriverLoginResponse
import com.sudhir.bhariya.Response.DriverResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.api.DriverAPI
import com.sudhir.bhariya.entity.Driver
import okhttp3.MultipartBody

class DriverRepository : MyApiRequest() {

    private val driverApi =
        ServiceBuilder.buildService(DriverAPI::class.java)

    suspend fun signupDriver(driver : Driver): DriverLoginResponse {
        return apiRequest {
            driverApi.signupDriver(driver)
        }
    }
    //driver check
    suspend fun checkDriver(phonenumber: String, password: String): DriverLoginResponse {
        return apiRequest {
            driverApi.checkDriver(phonenumber, password)
        }
    }

    suspend fun retrieveDriver(phonenumber: String): DriverResponse {
        return apiRequest {
            driverApi.retrieveDriver(phonenumber)
        }
    }

    suspend fun  viewDriver(): DriverResponse {
        return apiRequest {
            driverApi.viewDriver(ServiceBuilder.token!!)
        }
    }



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