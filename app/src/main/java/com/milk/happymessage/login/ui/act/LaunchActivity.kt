package com.milk.happymessage.login.ui.act

import android.animation.Animator
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.messaging.Constants
import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.account.Account
import com.milk.happymessage.app.AppConfig
import com.milk.happymessage.app.ui.act.MainActivity
import com.milk.happymessage.common.ad.AdConfig
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.constrant.KvKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.pay.PayManager
import com.milk.happymessage.common.ui.AbstractActivity
import com.milk.happymessage.databinding.ActivityLaunchBinding
import com.milk.happymessage.login.ui.vm.LaunchViewModel
import com.milk.happymessage.user.ui.act.UserInfoActivity
import com.milk.simple.ktx.immersiveStatusBar
import com.milk.simple.ktx.navigationBarPadding
import com.milk.simple.mdr.KvManger

class LaunchActivity : AbstractActivity() {
    private val binding by lazy { ActivityLaunchBinding.inflate(layoutInflater) }
    private val launchViewModel by viewModels<LaunchViewModel>()
    private var isNotLoaded = !AdConfig.isLoadedAds()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
        initializeNotification()
        initializeObserver()
        if (isNotLoaded) {
            uploadDeviceInfo()
            PayManager.getPayStatus()
            AppConfig.obtain()
            AdConfig.obtain()
            Account.initialize()
            checkIsNewClient()
        }
    }

    private fun initializeView() {
        immersiveStatusBar()
        binding.root.navigationBarPadding()
        binding.lottieView.setAnimation("launch.json")
        binding.lottieView.playAnimation()
        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(p0: Animator?) = Unit
            override fun onAnimationRepeat(p0: Animator?) = Unit
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {

            }
        })
    }

    private fun initializeNotification() {
        var map = mutableMapOf<String, String>()
        intent.extras?.let {
            map = Constants.MessagePayloadKeys.extractDeveloperDefinedPayload(intent.extras)
        }
        if (map.containsKey("userId")) {
            val userId = map["userId"]?.toLong()
            if (userId != null && userId > 0) {
                UserInfoActivity.create(this, userId)
                finish()
            }
        }
    }

    /** 运用启动开始加载广告、若加载成功且达到展示条件、则展示广告 */
    private fun initializeObserver() {
        // launchViewModel.getHasKey(this)
        LiveEventBus.get<Any?>(EventKey.UPDATE_START_AD_UNIT_ID).observe(this) {
            launchViewModel.loadLaunchAd(this) { toMainOrGenderPage() }
        }
    }

    private fun uploadDeviceInfo() {
        launchViewModel.uploadDeviceInfo()
    }

    private fun checkIsNewClient() {
        val isNewClient =
            KvManger.getBoolean(KvKey.CHECK_IS_NEW_CLIENT, true)
        if (isNewClient) {
            KvManger.put(KvKey.CHECK_IS_NEW_CLIENT, false)
            FireBaseManager.logEvent(FirebaseKey.FIRST_OPEN)
        }
    }

    private fun toMainOrGenderPage() {
        if (Account.userLogged || Account.userGender.isNotBlank()) {
            MainActivity.create(this)
        } else {
            GenderActivity.create(this)
        }
        finish()
    }

    override fun onInterceptKeyDownEvent(): Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        binding.lottieView.clearAnimation()
    }
}