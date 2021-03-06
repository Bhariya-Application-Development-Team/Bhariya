package com.sudhir.bhariya.Repository

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.Response.UserResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.UserAPI
import com.sudhir.bhariya.entity.User
import okhttp3.MultipartBody


class UserRepository : MyApiRequest(){
    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    suspend fun signupUser(user : User): LoginResponse {
        return apiRequest {
            userAPI.signupUser(user)
        }
    }

    suspend fun checkUser(phonenumber: String, password: String): LoginResponse {
        return apiRequest {
            userAPI.checkUser(phonenumber, password)
        }
    }

    suspend fun changePassword(phonenumber: String, password: String): LoginResponse {
        return apiRequest {
            userAPI.resetPassword(phonenumber, password)
        }
    }


    suspend fun  viewUser(): UserResponse {
        return apiRequest {
            userAPI.viewUser(ServiceBuilder.token!!)
        }
    }

    suspend fun updateUser(id : String, fullname : String, address : String, phonenumber : String, body : MultipartBody.Part): LoginResponse{
        return apiRequest {
            userAPI.updateUser(id = id, fullname = fullname, address = address, phonenumber = phonenumber,body)
        }
    }

    suspend fun updateUserText(id : String, fullname : String, address : String, phonenumber : String): LoginResponse{
        return apiRequest {
            userAPI.updateUserText(id = id, fullname = fullname, address = address, phonenumber = phonenumber)
        }
    }

}
