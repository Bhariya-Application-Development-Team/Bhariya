package com.sudhir.bhariya.dao

import androidx.room.*
import com.sudhir.bhariya.entity.User

@Dao
interface UserDAO {
    //Register user
    @Insert
    suspend fun SignupUser(user : User)

    @Query("SELECT * FROM User where username =(:username) and password=(:password)")
    suspend fun checkUser(username: String, password: String): User
}