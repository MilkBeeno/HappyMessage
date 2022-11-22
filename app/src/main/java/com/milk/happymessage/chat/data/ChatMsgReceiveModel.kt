package com.milk.happymessage.chat.data

import com.google.gson.annotations.SerializedName

data class ChatMsgReceiveModel(
    val faceAvatarUrl: String = "",
    val faceNickname: String = "",
    val faceUserId: Long = 0,
    @SerializedName("newChatMsgList")
    val chatMsgReceiveListModel: MutableList<ChatMsgReceiveSingleModel>? = null
)