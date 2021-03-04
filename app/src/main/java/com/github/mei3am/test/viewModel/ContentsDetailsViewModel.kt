package com.github.mei3am.test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.mei3am.test.apis.AppServices
import com.github.mei3am.test.models.request.ContentDetailsRequest
import com.github.mei3am.test.models.request.ContentRequest
import com.github.mei3am.test.models.request.ContentRequestModel
import com.github.mei3am.test.models.request.Request
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.utils.Klog
import com.github.mei3am.test.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ContentsDetailsViewModel @Inject constructor(private val appService: AppServices): ViewModel() {

    fun detailsQuery(contentId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val requestData = ContentDetailsRequest(ContentRequest(contentId))
            val response = appService.contentDetails(data = requestData)
            if (response.isSuccessful ){
                emit(Resource.success(response.body()))
            }else{
                emit(Resource.error(data = null, message = null))
            }
        } catch (exception: Exception) {
            Klog.e(exception)
            emit(Resource.error(data = null, message = null))
        }
    }

}