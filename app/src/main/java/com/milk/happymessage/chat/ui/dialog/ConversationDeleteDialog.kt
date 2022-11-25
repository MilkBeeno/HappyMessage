package com.milk.happymessage.chat.ui.dialog

import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.R
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogConversationDeleteBinding

class ConversationDeleteDialog(activity: FragmentActivity) :
    SimpleDialog<DialogConversationDeleteBinding>(activity) {
    private var clickRequest: ((Long) -> Unit)? = null
    private var conversationTargetId: Long = 0

    init {
        setGravity(Gravity.BOTTOM)
        setWidthMatchParent(true)
        setWindowAnimations(R.style.BottomDialog_Animation)
        binding.tvConfirm.setOnClickListener {
            dismiss()
            clickRequest?.invoke(conversationTargetId)
        }
        binding.tvCancel.setOnClickListener { dismiss() }
    }

    internal fun setConversationTargetId(targetId: Long) {
        this.conversationTargetId = targetId
    }

    internal fun setOnConfirmListener(clickRequest: (Long) -> Unit) {
        this.clickRequest = clickRequest
    }

    override fun getViewBinding(): DialogConversationDeleteBinding {
        return DialogConversationDeleteBinding.inflate(LayoutInflater.from(activity))
    }
}