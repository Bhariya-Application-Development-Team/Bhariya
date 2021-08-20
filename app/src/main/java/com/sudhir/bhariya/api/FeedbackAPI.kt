package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.entity.Feedback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackAPI {
    //
    @POST("/feedback_insert/")
    suspend fun insertFeedback(
        @Body feedback : Feedback
    ): Response<FeedbackResponse>

}