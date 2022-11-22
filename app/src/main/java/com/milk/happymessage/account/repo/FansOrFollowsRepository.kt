package com.milk.happymessage.account.repo

import com.milk.happymessage.account.api.FansOrFollowsApiService
import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit

class FansOrFollowsRepository {
    private val fansOrFollowsApiService =
        ApiClient.getMainRetrofit().create(FansOrFollowsApiService::class.java)

    internal suspend fun getFans(pageIndex: Int) = retrofit {
        fansOrFollowsApiService.getFans(pageIndex)
    }

    internal suspend fun getFollows(pageIndex: Int) = retrofit {
        fansOrFollowsApiService.getFollows(pageIndex)
    }
}