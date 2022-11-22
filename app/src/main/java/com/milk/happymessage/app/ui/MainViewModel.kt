package com.milk.happymessage.app.ui

import androidx.lifecycle.ViewModel
import com.milk.happymessage.chat.repo.MessageRepository
import com.milk.happymessage.common.author.AuthType
import com.milk.happymessage.common.author.DeviceManager
import com.milk.happymessage.login.repo.LoginRepository
import com.milk.simple.ktx.ioScope
import kotlinx.coroutines.flow.MutableSharedFlow

class MainViewModel : ViewModel() {
    internal val logOutFlow = MutableSharedFlow<Boolean>()

    internal fun uploadDeviceInfo() {
        val deviceId = DeviceManager.number
        val loginRepository = LoginRepository()
        ioScope {
            val apiResponse = loginRepository.login(deviceId, AuthType.NULL, deviceId)
            logOutFlow.emit(apiResponse.success)
        }
    }

    internal fun getConversationCount() = MessageRepository.getConversationCount()
}