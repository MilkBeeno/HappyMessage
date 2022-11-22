package com.milk.happymessage.account.ui.dialog

import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.R
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogDoubleConfirmBinding
import com.milk.simple.ktx.string

class DeleteMediaDialog(activity: FragmentActivity) :
    SimpleDialog<DialogDoubleConfirmBinding>(activity) {
    private var clickRequest: (() -> Unit)? = null

    init {
        setGravity(Gravity.BOTTOM)
        setWidthMatchParent(true)
        setWindowAnimations(R.style.BottomDialog_Animation)
        binding.tvTitle.text = activity.string(R.string.edit_profile_delete_image_title)
        binding.tvConfirm.text = activity.string(R.string.edit_profile_delete_image_confirm)
        binding.tvLogoutCancel.setOnClickListener { dismiss() }
        binding.tvConfirm.setOnClickListener {
            clickRequest?.invoke()
            dismiss()
        }
    }

    override fun getViewBinding(): DialogDoubleConfirmBinding {
        return DialogDoubleConfirmBinding.inflate(LayoutInflater.from(activity))
    }

    internal fun setOnConfirmListener(clickRequest: () -> Unit) {
        this.clickRequest = clickRequest
    }
}