package com.milk.happymessage.account.repo

import com.milk.happymessage.account.api.EditProfileApiService
import com.milk.happymessage.account.data.EditProfileBody
import com.milk.happymessage.common.net.ApiClient
import com.milk.happymessage.common.net.retrofit

class EditProfileRepository {
    private val editProfileApiService =
        ApiClient.getMainRetrofit().create(EditProfileApiService::class.java)

    internal suspend fun uploadProfile(
        avatarUrl: String,
        userName: String,
        userBio: String,
        userLink: String,
        videoImageUrl: String,
        videoUrl: String,
        imgList: ArrayList<String>,
    ) = retrofit {
        val body = EditProfileBody(
            avatarUrl,
            userName,
            userBio,
            userLink,
            videoImageUrl,
            videoUrl,
            imgList
        )
        editProfileApiService.uploadProfile(body)
    }
}