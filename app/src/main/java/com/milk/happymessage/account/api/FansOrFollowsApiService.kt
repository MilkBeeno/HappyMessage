package com.milk.happymessage.account.api

import com.milk.happymessage.account.data.FansOrFollowsModel
import com.milk.happymessage.common.response.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FansOrFollowsApiService {
    @FormUrlEncoded
    @POST("/funcall/pageFollowOrFans")
    suspend fun getFans(
        @Field("current") pageIndex: Int,
        @Field("ffType") type: String = "fans",
        @Field("size") size: Int = 8,
    ): ApiResponse<FansOrFollowsModel>

    @FormUrlEncoded
    @POST("/funcall/pageFollowOrFans")
    suspend fun getFollows(
        @Field("current") pageIndex: Int,
        @Field("ffType") type: String = "follow",
        @Field("size") size: Int = 8,
    ): ApiResponse<FansOrFollowsModel>
}