package com.sudhir.bhariya.Repository

import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.api.FeebackAPI
import com.sudhir.bhariya.entity.Feedback


class FeedbackRepository : MyApiRequest(){
    private val feedbackAPI =
        ServiceBuilder.buildService(FeebackAPI::class.java)

    suspend fun insertFeedback(feedback : Feedback): FeedbackResponse {
        return apiRequest {
            feedbackAPI.record_feedback(feedback)
        }
    }

}
