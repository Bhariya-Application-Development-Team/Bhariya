package com.sudhir.bhariya.dao

import androidx.room.*
import com.sudhir.bhariya.entity.User

@Dao
interface UserDAO {
    //Register user
    @Insert
    suspend fun SignupUser(user : User)


}