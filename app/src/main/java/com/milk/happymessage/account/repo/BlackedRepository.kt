package com.milk.happymessage.account.repo

import com.milk.happymessage.account.api.BlackedApiService
import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit

class BlackedRepository {
    private val blackApiService =
        ApiClient.getMainRetrofit().create(BlackedApiService::class.java)

    internal suspend fun getBlackedList(index: Int) = retrofit {
        blackApiService.getBlackedList(index)
    }
}