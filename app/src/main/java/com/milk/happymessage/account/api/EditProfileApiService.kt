package com.milk.happymessage.account.api

import com.milk.happymessage.account.data.EditProfileBody
import com.milk.happymessage.common.response.ApiResponse
import com.milk.happymessage.user.data.UserInfoModel
import retrofit2.http.Body
import retrofit2.http.POST

interface EditProfileApiService {
    @POST("/funcall/editProfile")
    suspend fun uploadProfile(
        @Body body: EditProfileBody
    ): ApiResponse<UserInfoModel>
}