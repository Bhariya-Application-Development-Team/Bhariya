package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.entity.Feedback
import com.sudhir.bhariya.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FeebackAPI {

    @POST("/feedback_insert/")
    suspend fun record_feedback(
        @Body feedback: Feedback
    ): Response<FeedbackResponse>

    @GET("/user_id_load/:id/")
    suspend fun getAllFeedback(
        @Body feedback: Feedback

    ): Response<FeedbackResponse>

    @POST("/user_id_load/")
    suspend fun showFeedback(
        @Body feedback: Feedback

    ):Response<FeedbackResponse>


}