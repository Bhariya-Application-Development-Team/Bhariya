package com.sudhir.bhariya.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sudhir.bhariya.dao.DriverDAO
import com.sudhir.bhariya.entity.Driver


@Database(

    entities = [(Driver::class)],
    version = 1,
    exportSchema =  false

)
abstract class DriverDB : RoomDatabase(){

    abstract fun getDriverDAO() : DriverDAO


    companion object{
        @Volatile
        private var instance : DriverDB? = null

        fun getInstance(context : Context) : DriverDB{
            if(instance == null){
                synchronized(DriverDB::class){
                    instance = buildDatabase(context)
                }
            }

            return instance!!
        }

        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DriverDB::class.java,
                "BhariyaDatabase"
            ).build()

    }
}
