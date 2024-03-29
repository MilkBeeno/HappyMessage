package com.milk.happymessage.common.net.error

import android.app.Application

interface ErrorHandler {
    fun initialize(application: Application)
    fun post(code: Int, message: String?)
}