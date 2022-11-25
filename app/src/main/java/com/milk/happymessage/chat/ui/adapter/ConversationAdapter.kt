package com.milk.happymessage.chat.ui.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import com.milk.happymessage.R
import com.milk.happymessage.account.Account
import com.milk.happymessage.chat.ui.time.convertMessageTime
import com.milk.happymessage.chat.ui.view.MessageRedDotView
import com.milk.happymessage.common.db.table.ConversationWithUserInfoEntity
import com.milk.happymessage.common.media.loader.ImageLoader
import com.milk.happymessage.common.paging.AbstractPagingAdapter
import com.milk.happymessage.common.paging.PagingViewHolder
import com.milk.happymessage.user.status.Gender
import com.milk.simple.ktx.dp2px

class ConversationAdapter : AbstractPagingAdapter<ConversationWithUserInfoEntity>(
    layoutId = R.layout.item_chat_converstaion,
    diffCallback = object : DiffUtil.ItemCallback<ConversationWithUserInfoEntity>() {
        override fun areItemsTheSame(
            oldItem: ConversationWithUserInfoEntity,
            newItem: ConversationWithUserInfoEntity
        ): Boolean {
            return oldItem.conversation.accountId == newItem.conversation.accountId
                && oldItem.conversation.targetId == newItem.conversation.targetId
                && oldItem.userInfo?.targetId == newItem.userInfo?.targetId
        }

        override fun areContentsTheSame(
            oldItem: ConversationWithUserInfoEntity,
            newItem: ConversationWithUserInfoEntity
        ): Boolean {
            return oldItem.conversation.targetName == newItem.conversation.targetName
                && oldItem.conversation.targetAvatar == newItem.conversation.targetAvatar
                && oldItem.conversation.messageType == newItem.conversation.messageType
                && oldItem.conversation.operationTime == newItem.conversation.operationTime
                && oldItem.conversation.messageContent == newItem.conversation.messageContent
                && oldItem.conversation.isAcceptMessage == newItem.conversation.isAcceptMessage
                && oldItem.conversation.unReadCount == newItem.conversation.unReadCount
                && oldItem.conversation.sendStatus == newItem.conversation.sendStatus
                && oldItem.conversation.putTopTime == newItem.conversation.putTopTime
                && oldItem.userInfo?.targetName == newItem.userInfo?.targetName
                && oldItem.userInfo?.targetAvatar == newItem.userInfo?.targetAvatar
        }
    }
) {
    override fun convert(holder: PagingViewHolder, item: ConversationWithUserInfoEntity) {
        holder.setVisible(R.id.ivTopOn, item.conversation.putTopTime > 0)
        ImageLoader.Builder()
            .loadAvatar(getTargetAvatar(item), getTargetGender(item))
            .target(holder.getView(R.id.ivUserAvatar))
            .build()
        holder.setText(R.id.tvUserName, getTargetName(item))
        holder.setText(R.id.tvMessage, item.conversation.messageContent)
        holder.setText(R.id.tvTime, item.conversation.operationTime.convertMessageTime())
        val redDotView = holder.getView<MessageRedDotView>(R.id.redDotRootView)
        redDotView.updateMessageCount(100)
        val params = redDotView.layoutParams as ConstraintLayout.LayoutParams
        if (item.conversation.unReadCount >= 99) {
            params.marginEnd = -redDotView.context.dp2px(10f).toInt()
        } else {
            params.marginEnd = 0
        }
        redDotView.layoutParams = params
    }

    private fun getTargetGender(conversationWithUserInfoEntity: ConversationWithUserInfoEntity): String {
        val userInfo = conversationWithUserInfoEntity.userInfo
        return when {
            userInfo != null -> userInfo.targetGender
            Account.userGender == Gender.Woman.value -> Gender.Man.value
            else -> Gender.Woman.value
        }
    }

    private fun getTargetName(conversationWithUserInfoEntity: ConversationWithUserInfoEntity): String {
        val userInfo = conversationWithUserInfoEntity.userInfo
        val conversation = conversationWithUserInfoEntity.conversation
        return userInfo?.targetName ?: conversation.targetName
    }

    private fun getTargetAvatar(conversationWithUserInfoEntity: ConversationWithUserInfoEntity): String {
        val userInfo = conversationWithUserInfoEntity.userInfo
        val conversation = conversationWithUserInfoEntity.conversation
        return userInfo?.targetAvatar ?: conversation.targetAvatar
    }
}