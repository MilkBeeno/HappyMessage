package com.milk.happymessage

import android.app.Application
import com.milk.happymessage.common.ad.AdManager
import com.milk.happymessage.common.author.DeviceManager
import com.milk.happymessage.common.author.FacebookAuth
import com.milk.happymessage.common.db.DataBaseManager
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.media.loader.LoaderConfig
import com.milk.happymessage.common.net.error.ApiErrorHandler
import com.milk.simple.ktx.ioScope
import com.milk.simple.log.Logger
import com.milk.simple.mdr.KvManger

class BaseApplication : Application() {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeLibrary()
    }

    private fun initializeLibrary() {
        ioScope {
            KvManger.initialize(instance)
            DeviceManager.initialize(instance)
            LoaderConfig.initialize(instance)
            Logger.initialize(BuildConfig.DEBUG)
            ApiErrorHandler.initialize(instance)
            DataBaseManager.initialize(instance)
            FacebookAuth.initializeSdk(instance)
            AdManager.initialize(instance)
            FireBaseManager.initialize(instance)
        }
    }
}