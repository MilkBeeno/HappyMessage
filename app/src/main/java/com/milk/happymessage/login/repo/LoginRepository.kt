package com.milk.happymessage.login.repo

import com.milk.happymessage.common.author.AuthType
import com.milk.happymessage.common.net.retrofit
import com.milk.happymessage.login.api.ApiService

class LoginRepository {
   internal suspend fun login(deviceNum: String, authType: AuthType, accessToken: String) = retrofit {
        ApiService.loginApiService.login(deviceNum, authType.value, accessToken)
    }
}