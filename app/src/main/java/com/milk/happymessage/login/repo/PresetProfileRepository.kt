package com.milk.happymessage.login.repo

import com.milk.happymessage.account.Account
import com.milk.happymessage.common.net.retrofit
import com.milk.happymessage.login.api.ApiService

class PresetProfileRepository {
    internal suspend fun getUserAvatarName(gender: String) = retrofit {
        ApiService.loginApiService.getUserAvatarName(gender)
    }

    internal suspend fun updateUserProfile(name: String, imageUrl: String) = retrofit {
        ApiService.loginApiService.updateUserProfile(name, imageUrl, Account.userGender)
    }
}