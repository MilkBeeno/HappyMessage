package com.milk.happymessage.user.ui.config

import com.milk.happymessage.R
import com.milk.happymessage.user.status.Gender

object GenderImage {
    internal fun obtain(gender: String): Int {
        return if (gender == Gender.Woman.value)
            R.drawable.common_gender_woman
        else
            R.drawable.common_gender_man
    }
}