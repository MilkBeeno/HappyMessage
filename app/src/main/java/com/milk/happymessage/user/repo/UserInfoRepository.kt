package com.milk.happymessage.user.repo

import com.milk.happymessage.account.Account
import com.milk.happymessage.app.AppConfig
import com.milk.happymessage.common.db.DataBaseManager
import com.milk.happymessage.common.db.table.UserInfoEntity
import com.milk.happymessage.common.net.retrofit
import com.milk.happymessage.user.api.ApiService
import com.milk.happymessage.user.data.UserInfoModel
import kotlinx.coroutines.flow.Flow

object UserInfoRepository {

    internal fun getUserInfoByDB(targetId: Long): Flow<UserInfoEntity?> {
        return DataBaseManager.DB.userInfoTableDao().query(targetId)
    }

    internal suspend fun getUserInfoByNetwork(userId: Long) = retrofit {
        val apiResponse =
            ApiService.userInfoApiService.getUserInfoByNetwork(userId)
        val apiResult = apiResponse.data
        if (apiResponse.success && apiResult != null && Account.userLogged)
            saveUserInfoToDB(apiResult)
        apiResponse
    }

    internal suspend fun getNextUserInfoByNetwork() = retrofit {
        val apiResponse =
            ApiService.userInfoApiService.getNextUserInfoByNetwork(Account.userGender)
        val apiResult = apiResponse.data
        if (apiResponse.success && apiResult != null && Account.userLogged)
            saveUserInfoToDB(apiResult)
        apiResponse
    }

    private fun saveUserInfoToDB(userInfo: UserInfoModel) {
        val userInfoEntity = UserInfoEntity()
        userInfoEntity.accountId = Account.userId
        userInfoEntity.targetId = userInfo.targetId
        userInfoEntity.targetName = userInfo.targetName
        userInfoEntity.targetAvatar = userInfo.targetAvatar
        userInfoEntity.targetGender = userInfo.targetGender
        userInfoEntity.targetImage = userInfo.targetImage
        userInfoEntity.targetVideo = userInfo.targetVideo
        userInfoEntity.targetOnline = userInfo.targetOnline
        userInfoEntity.targetIsFollowed = userInfo.targetIsFollowed
        userInfoEntity.targetIsBlacked = userInfo.targetIsBlacked
        DataBaseManager.DB.userInfoTableDao().insert(userInfoEntity)
    }

    internal suspend fun getUnlockInfo(deviceNumber: String, targetId: Long) =
        retrofit {
            ApiService.userInfoApiService.getUnlockInfo(
                deviceNumber,
                AppConfig.freeUnlockTimes,
                AppConfig.viewAdUnlockTimes,
                targetId
            )
        }

    internal suspend fun changeFollowedStatus(targetId: Long, isFollowed: Boolean) = retrofit {
        val apiResponse =
            ApiService.userInfoApiService.changeFollowedStatus(targetId, isFollowed)
        if (apiResponse.success) {
            // 更新本地关注数量
            val lastFollows = Account.userFollows
            Account.userFollows =
                if (isFollowed) lastFollows + 1 else lastFollows - 1
            Account.userFollowsFlow.emit(Account.userFollows)
            // 更新数据库中对当前用户的状态
            DataBaseManager.DB.userInfoTableDao()
                .updateFollowedStatus(Account.userId, targetId, isFollowed)
        }
        apiResponse
    }

    internal suspend fun blackUser(targetId: Long) = retrofit {
        ApiService.userInfoApiService.blackUser(targetId)
    }

    internal suspend fun changeUnlockStatus(deviceId: String, unlockType: Int, unlockUserId: Long) =
        retrofit {
            ApiService.userInfoApiService.changeUnlockStatus(deviceId, unlockType, unlockUserId)
        }
}