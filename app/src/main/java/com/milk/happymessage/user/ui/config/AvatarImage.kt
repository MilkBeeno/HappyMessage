package com.milk.happymessage.user.ui.config

import com.milk.happymessage.R
import com.milk.happymessage.user.status.Gender

class AvatarImage {
    internal fun obtain(gender: String): Int {
        return if (gender == Gender.Woman.value)
            R.drawable.common_default_woman
        else
            R.drawable.common_default_man
    }
}