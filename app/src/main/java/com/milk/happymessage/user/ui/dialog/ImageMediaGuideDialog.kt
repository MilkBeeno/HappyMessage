package com.milk.happymessage.user.ui.dialog

import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.milk.happymessage.common.ui.dialog.SimpleDialog
import com.milk.happymessage.databinding.DialogImageMediaBinding

class ImageMediaGuideDialog(activity: FragmentActivity) :
    SimpleDialog<DialogImageMediaBinding>(activity) {
    override fun getViewBinding(): DialogImageMediaBinding {
        return DialogImageMediaBinding.inflate(LayoutInflater.from(activity))
    }
}