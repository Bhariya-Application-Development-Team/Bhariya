package com.sudhir.bhariya.dao

import androidx.room.Dao
import androidx.room.Insert
import com.sudhir.bhariya.entity.Driver


@Dao
interface DriverDAO {
    //Register driver
    @Insert
    suspend fun SignupDriver(driver : Driver)


}