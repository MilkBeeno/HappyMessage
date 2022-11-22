package com.milk.happymessage.user.ui.config

import com.milk.happymessage.R
import com.milk.happymessage.user.status.Gender

class GenderImage {
    internal fun obtain(gender: String): Int {
        return if (gender == Gender.Woman.value)
            R.drawable.create_name_gender_woman
        else
            R.drawable.create_name_gender_man
    }
}