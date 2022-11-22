package com.milk.happymessage.common.ad.api

import com.milk.happymessage.common.ad.data.AdModel
import com.milk.happymessage.common.response.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AdApiService {
    @FormUrlEncoded
    @POST("/funcall/listAdPositionInfo")
    suspend fun getAdConfig(
        @Field("appId") appId: String,
        @Field("versionCode") versionCode: String,
        @Field("channelCode") channelCode: String
    ): ApiResponse<MutableList<AdModel>>
}