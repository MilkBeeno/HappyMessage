package com.milk.happymessage.app

import com.milk.happymessage.BaseApplication
import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit

class AppRepository {
    private val appService = ApiClient.getMainRetrofit().create(AppService::class.java)

    internal suspend fun getConfig(appId: String, pkgVersion: String, channel: String) = retrofit {
        appService.getConfig(appId, pkgVersion, channel)
    }

    internal suspend fun getSubscribeStatus(productId: String, purchaseToken: String) = retrofit {
        appService.getSubscribeStatus(
            BaseApplication.instance.packageName,
            productId,
            purchaseToken
        )
    }
}