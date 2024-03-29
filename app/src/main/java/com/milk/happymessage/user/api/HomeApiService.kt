package com.milk.happymessage.user.api

import com.milk.happymessage.common.response.ApiResponse
import com.milk.happymessage.user.data.HomModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeApiService {
    @FormUrlEncoded
    @POST("/funcall/pageIndex")
    suspend fun getHomeList(
        @Field("current") pageIndex: Int,
        @Field("gender") gender: String,
        @Field("groupNumber") groupNumber: Int,
        @Field("size") size: Int = 8,
    ): ApiResponse<HomModel>
}