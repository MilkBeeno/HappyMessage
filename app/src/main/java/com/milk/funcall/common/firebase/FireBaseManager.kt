package com.milk.funcall.common.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FireBaseManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    internal fun initialize(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    internal fun logEvent(name: String, key: String = "", value: String = "") {
        if (this::firebaseAnalytics.isInitialized) {
            var bundle: Bundle? = null
            if (key.isNotBlank() && value.isNotBlank()) {
                bundle = Bundle()
                bundle.putString(key, value)
            }
            firebaseAnalytics.logEvent(name, bundle)
        }
    }
}