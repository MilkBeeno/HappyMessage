package com.milk.happymessage.user.api

import com.milk.happymessage.common.response.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ReportApiService {
    @FormUrlEncoded
    @POST("/funcall/tipOff")
    suspend fun report(
        @Field("userId") userId: Long,
        @Field("reportType") reportType: Int,
    ): ApiResponse<Any?>
}