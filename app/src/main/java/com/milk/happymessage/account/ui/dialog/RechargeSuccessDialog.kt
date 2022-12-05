package com.milk.happymessage.account.ui.dialog

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogRechargeSuccessfulBinding

class RechargeSuccessDialog(activity: FragmentActivity) :
    SimpleDialog<DialogRechargeSuccessfulBinding>(activity) {
    private val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    init {
        setGravity(Gravity.CENTER)
        setWidthMatchParent(true)
        setCanceledOnTouchOutside(true)
        handler.postDelayed({ dismiss() }, 1500)
    }

    override fun getViewBinding(): DialogRechargeSuccessfulBinding {
        return DialogRechargeSuccessfulBinding.inflate(LayoutInflater.from(activity))
    }
}