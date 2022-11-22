package com.milk.happymessage.account.ui.vm

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.milk.happymessage.account.repo.FansOrFollowsRepository
import com.milk.happymessage.common.db.table.UserInfoEntity
import com.milk.happymessage.common.paging.NetworkPagingSource
import com.milk.happymessage.common.response.ApiPagingResponse

class FollowsViewModel : ViewModel() {
    private val fansOrFollowsRepository by lazy { FansOrFollowsRepository() }
    internal val pagingSource = Pager(
        config = PagingConfig(
            pageSize = 8,
            prefetchDistance = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NetworkPagingSource { getFollows(it) }
        }
    )

    private suspend fun getFollows(index: Int): ApiPagingResponse<UserInfoEntity> {
        val apiResponse = fansOrFollowsRepository.getFollows(index)
        val apiResult = apiResponse.data?.records
        return ApiPagingResponse(
            code = apiResponse.code,
            message = apiResponse.message,
            data = apiResult
        )
    }
}