package com.milk.happymessage.account.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.milk.happymessage.R
import com.milk.happymessage.account.data.BlackedModel
import com.milk.happymessage.common.media.loader.ImageLoader
import com.milk.happymessage.common.paging.AbstractPagingAdapter
import com.milk.happymessage.common.paging.PagingViewHolder
import com.milk.happymessage.common.ui.view.GenderImageView

class BlackedListAdapter : AbstractPagingAdapter<BlackedModel>(
    layoutId = R.layout.item_black_list,
    diffCallback = object : DiffUtil.ItemCallback<BlackedModel>() {
        override fun areItemsTheSame(oldItem: BlackedModel, newItem: BlackedModel): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: BlackedModel, newItem: BlackedModel): Boolean {
            return oldItem.userName == newItem.userName
                && oldItem.userAvatar == newItem.userAvatar
                && oldItem.userGender == newItem.userGender
        }
    }
) {
    init {
        addChildClickViewIds(R.id.ivUserAvatar)
    }

    override fun convert(holder: PagingViewHolder, item: BlackedModel) {
        holder.setText(R.id.tvUserName, item.userName)
        ImageLoader.Builder()
            .loadAvatar(item.userAvatar, item.userGender)
            .target(holder.getView(R.id.ivUserAvatar))
            .build()
        holder.getView<GenderImageView>(R.id.ivUserGender).updateGender(item.userGender)
    }
}