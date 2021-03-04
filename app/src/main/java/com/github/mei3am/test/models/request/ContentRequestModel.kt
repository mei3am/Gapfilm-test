package com.github.mei3am.test.models.request

import com.google.gson.annotations.SerializedName

data class ContentRequestModel (
    val request: Request
)
data class Request(
    @SerializedName("RequestType")
    val requestType: Int = 2,
    @SerializedName("RequestId")
    val requestId: Int? = null,
    @SerializedName("PageSize")
    val pageSize: Int = 10,
    @SerializedName("PageIndex")
    val pageIndex: Int,
    @SerializedName("OrderBy")
    val orderBy: String = "createdate",
    @SerializedName("Order")
    val order: String = "desc"
)