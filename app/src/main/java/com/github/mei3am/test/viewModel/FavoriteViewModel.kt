package com.github.mei3am.test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.mei3am.test.apis.AppServices
import com.github.mei3am.test.models.request.ContentRequestModel
import com.github.mei3am.test.models.request.Request
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.repository.AppDb
import com.github.mei3am.test.utils.Klog
import com.github.mei3am.test.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val appDb: AppDb): ViewModel() {
    val contentList = mutableListOf<Content>()

    fun loadFromDbQuery() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = appDb.billBasketDao().getAll()
            emit(Resource.success(response))
            contentList.removeAll(contentList)
            contentList.addAll(response!!)
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = null))
        }
    }

    fun deleteFromDbQuery(contentId: Int, position: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            appDb.billBasketDao().delete(contentId)
            contentList.removeAt(position)
            emit(Resource.success(contentList))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = null))
        }
    }
}