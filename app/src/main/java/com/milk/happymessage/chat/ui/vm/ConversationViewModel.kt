package com.milk.happymessage.chat.ui.vm

import androidx.lifecycle.ViewModel
import com.milk.happymessage.chat.repo.MessageRepository
import com.milk.happymessage.common.db.table.ConversationWithUserInfoEntity
import com.milk.happymessage.common.paging.LocalPagingSource
import com.milk.simple.ktx.ioScope

class ConversationViewModel : ViewModel() {
    internal val pagingSource: LocalPagingSource<Int, ConversationWithUserInfoEntity>
        get() {
            return LocalPagingSource(
                pageSize = 20,
                prefetchDistance = 5,
                pagingSourceFactory = {
                    MessageRepository.getChatConversationByDB()
                }
            )
        }

    internal fun putTopChatMessage(targetId: Long) {
        ioScope { MessageRepository.putTopChatMessage(targetId) }
    }

    internal fun unPinChatMessage(targetId: Long) {
        ioScope { MessageRepository.unPinChatMessage(targetId) }
    }

    internal fun deleteChatMessage(targetId: Long) {
        ioScope { MessageRepository.deleteChatMessage(targetId) }
    }
}