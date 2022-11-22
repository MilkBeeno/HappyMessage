package com.milk.happymessage.chat.api

import com.milk.happymessage.chat.data.ChatMsgReceiveModel
import com.milk.happymessage.chat.data.ChatMsgSentTextModel
import com.milk.happymessage.chat.data.HeartBeatModel
import com.milk.happymessage.common.response.ApiResponse
import retrofit2.http.*

interface ChatApiService {
    @GET("/funcall/heartBeat")
    suspend fun heartBeat(): ApiResponse<HeartBeatModel>

    @GET("/funcall/listChatNewMessage")
    suspend fun getMessagesFromNetwork(): ApiResponse<MutableList<ChatMsgReceiveModel>>

    @FormUrlEncoded
    @Headers("Encoded:false")
    @POST("/funcall/sendMsg")
    suspend fun sendTextChatMessage(
        @Field("faceUserId") targetId: Long,
        @Field("content") content: String
    ): ApiResponse<ChatMsgSentTextModel>
}