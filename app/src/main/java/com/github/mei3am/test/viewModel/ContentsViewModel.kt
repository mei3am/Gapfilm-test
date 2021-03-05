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

class ContentsViewModel @Inject constructor(private val appService: AppServices, private val appDb: AppDb): ViewModel() {
    val contentList = mutableListOf<Content>()
    val favoriteList = mutableListOf<Content>()
    var isLoadData = false
    private var pageIndex: Int = 1
    fun contentQuery() = liveData(Dispatchers.IO) {
        isLoadData = true
        emit(Resource.loading(data = null))
        try {
            val requestData = ContentRequestModel(Request(pageIndex = pageIndex))
            val response = appService.content(data = requestData)
            val fList = appDb.billBasketDao().getAll()
            if (response.isSuccessful ){
                if (fList?.isNotEmpty() == true){
                    response.body()?.result?.contentList!!.forEach{ item ->
                        val filter = fList.filter { fItem -> item.contentId ==  fItem.contentId}
                        if (!filter.isNullOrEmpty()){
                            item.favorite = true
                        }
                        contentList.add(item)
                    }
                }else{
                    contentList.addAll(response.body()?.result?.contentList!!)
                }
                emit(Resource.success(contentList))
                favoriteList.removeAll(favoriteList)
                favoriteList.addAll(favoriteList)
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

    fun deleteFromFavoriteDbQuery(contentId: Int, position: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            appDb.billBasketDao().delete(contentId)
            contentList[position].favorite = false
            emit(Resource.success(contentList))
        } catch (exception: Exception) {
            Klog.e(exception)
            emit(Resource.error(data = null, message = null))
        }
    }

    fun insertToFavoriteDbQuery(content: Content, position: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            appDb.billBasketDao().insert(content)
            contentList[position].favorite = true
            emit(Resource.success(contentList))
        } catch (exception: Exception) {
            Klog.e(exception)
            emit(Resource.error(data = null, message = null))
        }
    }

    fun loadDbQuery() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val newFavoriteList = appDb.billBasketDao().getAll()!!


            if (newFavoriteList.size == favoriteList.size){
                emit(Resource.success(listOf<Int>()))
            }else {
                val listOfChanges = mutableListOf<Int>()
                contentList.forEachIndexed { index, content ->
                    if (!newFavoriteList.contains(content)){
                        if (contentList[index].favorite){
                            contentList[index].favorite = false
                            listOfChanges.add(index)
                        }
                    }
                }
                emit(Resource.success(listOfChanges))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            Klog.e(exception)
            emit(Resource.error(data = null, message = null))
        }
    }

    private fun listDiff(list1: List<Content>, list2: List<Content>): List<Content>{
        val sum = list1 + list2
        return sum.groupBy { it.contentId }
                .filter { it.value.size == 1 }
                .flatMap { it.value }
    }
}