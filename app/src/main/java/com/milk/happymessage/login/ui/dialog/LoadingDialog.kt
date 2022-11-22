package com.milk.happymessage.login.ui.dialog

import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.R
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogLoadingBinding
import com.milk.simple.ktx.string

class LoadingDialog(
    activity: FragmentActivity,
    content: String = activity.string(R.string.common_loading)
) :
    SimpleDialog<DialogLoadingBinding>(activity) {
    init {
        setDimAmount(0.1f)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        binding.tvContent.text = content
    }

    override fun getViewBinding(): DialogLoadingBinding {
        return DialogLoadingBinding.inflate(LayoutInflater.from(activity))
    }
}