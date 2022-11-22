package com.milk.happymessage.account.api

import com.milk.happymessage.account.data.BlackedListModel
import com.milk.happymessage.common.response.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BlackedApiService {
    @FormUrlEncoded
    @POST("/funcall/pageBlackedList")
    suspend fun getBlackedList(
        @Field("current") index: Int,
        @Field("size") size: Int = 20
    ): ApiResponse<BlackedListModel>
}