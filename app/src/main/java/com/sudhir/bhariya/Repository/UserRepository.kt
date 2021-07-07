package com.sudhir.bhariya.Repository



import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.UserAPI
import com.sudhir.bhariya.entity.User
import okhttp3.MultipartBody


class UserRepository : MyApiRequest(){
    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    suspend fun SignupUser(user : User): LoginResponse {
        return apiRequest {
            userAPI.SignupUser(user)
        }
    }

    suspend fun checkUser(username: String, password: String): LoginResponse {
        return apiRequest {
            userAPI.checkUser(username, password)
        }
    }

}
