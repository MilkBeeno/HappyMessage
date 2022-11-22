package com.milk.happymessage.user.repo

import com.milk.happymessage.account.Account
import com.milk.happymessage.common.net.retrofit
import com.milk.happymessage.user.api.ApiService

class HomeRepository {
    internal suspend fun getHomeList(pageIndex: Int, groupNumber: Int) = retrofit {
        ApiService.homeApiService.getHomeList(pageIndex, Account.userGender, groupNumber)
    }
}