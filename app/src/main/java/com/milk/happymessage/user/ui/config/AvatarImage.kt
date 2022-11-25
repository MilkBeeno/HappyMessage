package com.milk.happymessage.user.ui.config

import com.milk.happymessage.R
import com.milk.happymessage.user.status.Gender

object AvatarImage {
    internal fun obtain(gender: String): Int {
        return if (gender == Gender.Woman.value)
            R.drawable.common_default_avatar_woman
        else
            R.drawable.common_default_avatar_man
    }
}