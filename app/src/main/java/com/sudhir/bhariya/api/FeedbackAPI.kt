package com.sudhir.bhariya.api

import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.entity.Feedback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FeedbackAPI {
    //
    @POST("/feedback_insert/")
    suspend fun userFeedback(
        @Body feedback : Feedback
    ): Response<FeedbackResponse>

    @GET("/user_id_load/60ed4242becc50426028cc91")
    suspend fun getAllFeedback(
        @Header("Authorization") token: String

    ): Response<FeedbackResponse>

    @POST("/user_id_load/60ed4242becc50426028cc91")
    suspend fun showFeedback(
        @Body feedback: Feedback

    ):Response<FeedbackResponse>

}