package com.github.mei3am.test.models.request

import com.google.gson.annotations.SerializedName

data class ContentDetailsRequest(
    val request: ContentRequest
)

data class ContentRequest(
    @SerializedName("RequestID")
    val requestId: Int
)