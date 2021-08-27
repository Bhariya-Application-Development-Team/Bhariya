package com.sudhir.bhariya.dao

import androidx.room.*
import com.sudhir.bhariya.entity.Feedback

interface FeedbackDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedback(feedback: Feedback)

    @Query("SELECT * FROM Feedback")
    suspend fun getAllFeedback(): List<Feedback>

//    @Update
//    suspend fun updateUniversity(university : University)
//
//    @Delete
//    suspend fun deleteUniversity(university : University)
}