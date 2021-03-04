package com.github.mei3am.test.apis

import com.github.mei3am.test.models.request.ContentRequestModel
import com.github.mei3am.test.models.response.ContentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppServices {

    @POST("request.asmx/GetContentList")
    suspend fun content(
        @Body data: ContentRequestModel
    ): Response<ContentResponse>


}