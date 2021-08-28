package com.sudhir.bhariya.Repository

import com.sudhir.bhariya.MyApiRequest
import com.sudhir.bhariya.Response.FeedbackResponse
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.api.FeedbackAPI
//import com.sudhir.bhariya.ServiceBuilder
//import com.sudhir.bhariya.FeedbackAPI
import com.sudhir.bhariya.entity.Feedback


class FeedbackRepository : MyApiRequest(){
    private val feedbackAPI =
        ServiceBuilder.buildService(FeedbackAPI::class.java)

    suspend fun insertFeedback(feedback : Feedback): FeedbackResponse {
        return apiRequest {
            feedbackAPI.insertFeedback(feedback)
        }
    }

    suspend fun showFeedback(feedback : Feedback): FeedbackResponse {
        return apiRequest {
            feedbackAPI.showFeedback(feedback)
        }
    }

//    private val feedbackAPI =
//        ServiceBuilder.buildService(FeedbackAPI::class.java)
//
//    suspend fun userFeedback(feedback : Feedback): FeedbackResponse {
//        return apiRequest {
//            feedbackAPI.userFeedback(feedback)
//        }
//    }

//    suspend fun getAllFeedback(feedback : Feedback):FeedbackResponse{
//        return apiRequest {
//            feedbackAPI.getAllFeedback(feedback)
//        }
//    }
}
