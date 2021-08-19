package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.Response.LoginResponse
import com.sudhir.bhariya.entity.Feedback
import com.sudhir.bhariya.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeebackAPI {

    @POST("/feedback_insert/")
    suspend fun record_feedback(
        @Body feedback: Feedback
    ): Response<FeedbackResponse>

}