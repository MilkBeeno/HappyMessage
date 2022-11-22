package com.milk.happymessage.login.ui.dialog

import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogMaxClientBinding

class MaxClientDialog(activity: FragmentActivity) : SimpleDialog<DialogMaxClientBinding>(activity) {
    init {
        setDimAmount(0.1f)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        binding.ivClose.setOnClickListener { dismiss() }
    }

    override fun getViewBinding(): DialogMaxClientBinding {
        return DialogMaxClientBinding.inflate(LayoutInflater.from(activity))
    }
}