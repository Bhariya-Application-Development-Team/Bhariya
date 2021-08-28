package com.sudhir.bhariya.Response

import com.sudhir.bhariya.entity.Feedback

data class FeedbackResponse(
    val success : Boolean?= null,
    val feedback : List<Feedback>? = null,
    val Feedback : String?= null,
    val Username : String?= null,

    )