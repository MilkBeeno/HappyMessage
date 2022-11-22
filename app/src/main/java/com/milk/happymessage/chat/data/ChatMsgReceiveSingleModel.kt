package com.milk.happymessage.chat.data

data class ChatMsgReceiveSingleModel(
    val chatTime: String = "",
    val content: String = "",
    val itemId: String = "",
    var chatTimeStamp: Long = 0
)