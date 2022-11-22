package com.milk.happymessage.login.api

import com.milk.happymessage.common.net.ApiClient

object ApiService {
    internal val loginApiService: LoginApiService =
        ApiClient.getMainRetrofit().create(LoginApiService::class.java)
}