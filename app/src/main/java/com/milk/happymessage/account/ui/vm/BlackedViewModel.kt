package com.milk.happymessage.account.ui.vm

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.milk.happymessage.account.data.BlackedModel
import com.milk.happymessage.account.repo.BlackedRepository
import com.milk.happymessage.common.paging.NetworkPagingSource
import com.milk.happymessage.common.response.ApiPagingResponse

class BlackedViewModel : ViewModel() {
    private val blackedRepository by lazy { BlackedRepository() }
    val pagingSource = Pager(
        config =  PagingConfig(
            pageSize = 20,
            prefetchDistance = 4,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NetworkPagingSource { getBlackedList(it) }
        }
    )

    private suspend fun getBlackedList(index: Int): ApiPagingResponse<BlackedModel> {
        val apiResponse = blackedRepository.getBlackedList(index)
        val apiResult = apiResponse.data?.records
        return ApiPagingResponse(
            code = apiResponse.code,
            message = apiResponse.message,
            data = apiResult
        )
    }
}