package com.milk.happymessage.user.ui.dialog

import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogViewAdBinding
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.constrant.FirebaseKey

class ViewAdDialog(activity: FragmentActivity) : SimpleDialog<DialogViewAdBinding>(activity) {
    private var confirmRequest: (() -> Unit)? = null

    init {
        setGravity(Gravity.CENTER)
        setWidthMatchParent(true)
        setCanceledOnTouchOutside(true)
        binding.ivClose.setOnClickListener {
            FireBaseManager
                .logEvent(FirebaseKey.CLICK_CANCEL_SHOW_CONTACT_DOUBLE_CHECK)
            dismiss()
        }
        binding.tvConfirm.setOnClickListener {
            FireBaseManager
                .logEvent(FirebaseKey.CLICK_CONFIRM_CONTACT_DOUBLE_CHECK)
            confirmRequest?.invoke()
            dismiss()
        }
    }

    internal fun setOnConfirmRequest(confirmRequest: () -> Unit) {
        this.confirmRequest = confirmRequest
    }

    override fun getViewBinding(): DialogViewAdBinding {
        return DialogViewAdBinding.inflate(activity.layoutInflater)
    }
}