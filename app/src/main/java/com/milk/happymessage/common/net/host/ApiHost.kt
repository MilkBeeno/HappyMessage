package com.milk.happymessage.common.net.host

import com.milk.happymessage.BuildConfig

interface ApiHost {
    val realUrl: String
        get() = if (BuildConfig.DEBUG) debugUrl() else releaseUrl()

    fun releaseUrl(): String
    fun debugUrl(): String
}