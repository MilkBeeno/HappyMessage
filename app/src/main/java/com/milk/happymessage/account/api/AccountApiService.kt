package com.milk.happymessage.account.api

import com.milk.happymessage.common.response.ApiResponse
import com.milk.happymessage.user.data.UserInfoModel
import retrofit2.http.GET

interface AccountApiService {
    @GET("/funcall/currentUserInfo")
    suspend fun getUserInfo(): ApiResponse<UserInfoModel>
}