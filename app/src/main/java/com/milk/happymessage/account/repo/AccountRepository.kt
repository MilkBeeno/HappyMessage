package com.milk.happymessage.account.repo

import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.account.Account
import com.milk.happymessage.account.api.AccountApiService
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit
import com.milk.simple.ktx.ioScope

object AccountRepository {
    private val accountApiService =
        ApiClient.getMainRetrofit().create(AccountApiService::class.java)

    internal fun getAccountInfo(registered: Boolean) {
        ioScope {
            val apiResponse = retrofit { accountApiService.getUserInfo() }
            val apiResult = apiResponse.data
            if (apiResponse.success && apiResult != null) {
                Account.saveAccountInfo(apiResult, registered)
                LiveEventBus.get<Boolean>(EventKey.REFRESH_HOME_LIST).post(true)
            }
        }
    }
}