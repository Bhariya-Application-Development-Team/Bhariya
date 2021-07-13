package com.sudhir.bhariya.Repository



import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.UserAPI
import com.sudhir.bhariya.entity.User



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

}
