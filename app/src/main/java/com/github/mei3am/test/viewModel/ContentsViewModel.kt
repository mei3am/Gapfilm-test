package com.github.mei3am.test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.mei3am.test.apis.AppServices
import com.github.mei3am.test.models.request.ContentRequestModel
import com.github.mei3am.test.models.request.Request
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.utils.Klog
import com.github.mei3am.test.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ContentsViewModel @Inject constructor(private val appService: AppServices): ViewModel() {
    var pageIndex: Int = 1
    val contentList = mutableListOf<Content>()
    var isLoadData = false

    fun contentQuery() = liveData(Dispatchers.IO) {
        isLoadData = true
        emit(Resource.loading(data = null))
        try {
            val requestData = ContentRequestModel(Request(pageIndex = pageIndex))
            val response = appService.content(data = requestData)
            if (response.isSuccessful ){
                contentList.addAll(response.body()!!.result.contentList)
                emit(Resource.success(contentList))
                pageIndex += 1
            }else{
                emit(Resource.error(data = null, message = null))
            }
        } catch (exception: Exception) {
            Klog.e(exception)
            emit(Resource.error(data = null, message = null))
        }
        isLoadData = false
    }

}