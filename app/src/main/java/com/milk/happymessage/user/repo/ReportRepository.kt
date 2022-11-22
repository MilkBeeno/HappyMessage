package com.milk.happymessage.user.repo

import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit
import com.milk.happymessage.user.api.ReportApiService
import com.milk.happymessage.user.status.ReportType

object ReportRepository {
    private val reportApiService =
        ApiClient.getMainRetrofit().create(ReportApiService::class.java)

    internal suspend fun report(userId: Long, type: ReportType) = retrofit {
        reportApiService.report(userId, type.value)
    }
}