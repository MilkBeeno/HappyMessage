package com.milk.happymessage.user.ui.act

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.R
import com.milk.happymessage.account.Account
import com.milk.happymessage.account.ui.act.RechargeActivity
import com.milk.happymessage.app.AppConfig
import com.milk.happymessage.chat.ui.act.ChatMessageActivity
import com.milk.happymessage.common.ad.AdConfig
import com.milk.happymessage.common.author.DeviceManager
import com.milk.happymessage.common.constrant.AdCodeKey
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.constrant.KvKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.media.loader.ImageLoader
import com.milk.happymessage.common.paging.SimpleGridDecoration
import com.milk.happymessage.common.ui.AbstractActivity
import com.milk.happymessage.common.ui.manager.NoScrollGridLayoutManager
import com.milk.happymessage.databinding.ActivityUserInfoBinding
import com.milk.happymessage.login.ui.act.LoginActivity
import com.milk.happymessage.login.ui.dialog.LoadingDialog
import com.milk.happymessage.user.status.UnlockType
import com.milk.happymessage.user.ui.adapter.UserImageAdapter
import com.milk.happymessage.user.ui.dialog.ReportDialog
import com.milk.happymessage.user.ui.dialog.ViewAdDialog
import com.milk.happymessage.user.ui.dialog.ViewLinkDialog
import com.milk.happymessage.user.ui.vm.UserInfoViewModel
import com.milk.simple.ktx.*
import kotlin.math.abs

class UserInfoActivity : AbstractActivity() {
    private val binding by lazy { ActivityUserInfoBinding.inflate(layoutInflater) }
    private val userInfoViewModel by viewModels<UserInfoViewModel>()
    private val userId by lazy { intent.getLongExtra(USER_ID, 0) }
    private val loadingDialog by lazy { LoadingDialog(this) }
    private val viewAdDialog by lazy { ViewAdDialog(this) }
    private val viewLinkDialog by lazy { ViewLinkDialog(this) }
    private val reportDialog by lazy { ReportDialog(this) }

    /** 值是正代表下拉 ，是负值代表上滑 */
    private var slidingDistance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
        initializeObserver()
        loadUserInfo()
    }

    private fun initializeView() {
        immersiveStatusBar()
        binding.flHeaderToolbar.statusBarPadding()
        binding.root.navigationBarPadding()
        binding.ivBack.setOnClickListener(this)
        binding.ivReport.setOnClickListener(this)
        binding.llFollow.setOnClickListener(this)
        binding.llCopy.setOnClickListener(this)
        binding.flVideo.setOnClickListener(this)
        binding.flVideoLocked.setOnClickListener(this)
        binding.clViewLink.setOnClickListener(this)
        binding.clViewVideo.setOnClickListener(this)
        binding.flPhotoLocked.setOnClickListener(this)
        binding.clViewPhoto.setOnClickListener(this)
        binding.tvMessage.setOnClickListener(this)
        binding.llNext.setOnClickListener(this)
        reportDialog.setReportListener {
            loadingDialog.show()
            userInfoViewModel.report(userId, it)
        }
        binding.slContent.setOnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val current = v as NestedScrollView
            if (scrollY > oldScrollY) {
                if (slidingDistance > 0) slidingDistance = 0
                if (scrollY - oldScrollY > 30) {
                    binding.vHeaderToolbarColor.alpha = 1f
                } else {
                    slidingDistance -= scrollY - oldScrollY
                    binding.vHeaderToolbarColor.alpha = abs(slidingDistance) / 40f * 1f
                }
            } else {
                if (slidingDistance < 0) slidingDistance = 0
                if (oldScrollY - scrollY > 30) {
                    binding.vHeaderToolbarColor.alpha = 0f
                } else {
                    slidingDistance += oldScrollY - scrollY
                    binding.vHeaderToolbarColor.alpha = 1f - abs(slidingDistance) / 40f * 1f
                }
            }
            when (scrollY) {
                0 -> binding.vHeaderToolbarColor.alpha = 0f
                current.getChildAt(0).measuredHeight - current.measuredHeight -> {
                    binding.vHeaderToolbarColor.alpha = 1f
                }
            }
        }
    }

    private fun initializeObserver() {
        userInfoViewModel.loadUserInfoStatusFlow.collectLatest(this) {
            if (it) {
                binding.lvLoading.gone()
                binding.slContent.visible()
                binding.llUserBottom.visible()
                setUserAvatar()
                setUserName()
                setUserFollow()
                setUserId()
                setUserBio()
                setUserLink()
                val videoEmpty = setUserVideo()
                val imageEmpty = setUserImage()
                if (videoEmpty && imageEmpty) binding.ivMediaEmpty.visible()
            } else {
                showToast(string(R.string.user_info_obtain_failed))
                finish()
            }
        }
        userInfoViewModel.changeFollowedStatusFlow.collectLatest(this) {
            loadingDialog.dismiss()
            if (it) {
                setUserFollow()
                showToast(string(R.string.common_success))
            }
        }
        userInfoViewModel.changeUnlockStatusFlow.collectLatest(this) {
            loadingDialog.dismiss()
            if (it) {
                val userInfo = userInfoViewModel.getUserInfoModel()
                if (!userInfo.linkUnlocked) {
                    updateMediaFreeTimes(binding.ivLinkType, binding.tvLinkTimes)
                }
                if (!userInfo.imageUnlocked) {
                    updateMediaFreeTimes(binding.ivPhotoType, binding.tvPhotoTimes)
                }
            }
        }
        LiveEventBus.get<Pair<Boolean, Long>>(EventKey.USER_FOLLOWED_STATUS_CHANGED)
            .observe(this) {
                if (it.second == userId) {
                    userInfoViewModel.getUserInfoModel().targetIsFollowed = it.first
                    setUserFollow()
                }
            }
        userInfoViewModel.reportFlow.collectLatest(this) {
            loadingDialog.dismiss()
            if (it) showLongToast(string(R.string.common_report_successful))
        }
    }

    private fun updateMediaFreeTimes(
        targetImage: AppCompatImageView,
        targetText: AppCompatTextView
    ) {
        val userInfo = userInfoViewModel.getUserInfoModel()
        val maxTimes = if (userInfo.unlockMethod == 1) {
            targetImage.setImageResource(R.drawable.user_info_media_locked_view)
            AppConfig.freeUnlockTimes
        } else {
            targetImage.setImageResource(R.drawable.user_info_media_locked_view_ad)
            AppConfig.viewAdUnlockTimes
        }
        if (maxTimes < 10) {
            targetText.visible()
            targetText.text =
                "(".plus(string(R.string.user_info_unlock_times))
                    .plus(" ")
                    .plus(userInfo.remainUnlockCount)
                    .plus("/")
                    .plus(maxTimes)
                    .plus(")")
        } else targetText.gone()
    }

    private fun setUserFollow() {
        binding.llFollow.visible()
        val params = binding.ivFollow.layoutParams as LinearLayoutCompat.LayoutParams
        if (userInfoViewModel.getUserInfoModel().targetIsFollowed) {
            binding.tvFollow.gone()
            binding.ivFollow.setImageResource(R.drawable.user_info_followed)
            binding.llFollow.setBackgroundResource(R.drawable.shape_user_info_followed)
            params.marginStart = dp2px(8f).toInt()
            params.marginEnd = dp2px(8f).toInt()
            binding.ivFollow.layoutParams = params
        } else {
            binding.tvFollow.visible()
            binding.ivFollow.setImageResource(R.drawable.user_info_un_follow)
            binding.llFollow.setBackgroundResource(R.drawable.shape_user_info_un_follow)
            params.marginStart = dp2px(10f).toInt()
            params.marginEnd = 0
            binding.ivFollow.layoutParams = params
        }
    }

    private fun setUserAvatar() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        ImageLoader.Builder()
            .loadAvatar(userInfo.targetAvatar, userInfo.targetGender)
            .target(binding.ivUserAvatar)
            .build()
    }

    private fun setUserName() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        binding.tvUserName.text = userInfo.targetName
    }

    private fun setUserId() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        binding.tvUserId.text = "ID : ".plus(userInfo.targetIdx)
    }

    @SuppressLint("SetTextI18n")
    private fun setUserBio() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        if (userInfo.targetBio.isNotBlank()) {
            binding.tvUserBio.text = userInfo.targetBio
        } else {
            binding.tvUserBio.text = string(R.string.user_info_not_bio)
        }
    }

    private fun setUserLink() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        when {
            userInfo.targetLink.isNotBlank() -> {
                binding.tvContact.text = userInfo.targetLink
                binding.tvContact.visible()
                binding.llCopy.visible()
                binding.tvNotLink.gone()
                val adUnitId = AdConfig.getAdvertiseUnitId(AdCodeKey.VIEW_USER_LINK)
                if (!Account.userSubscribe && adUnitId.isNotBlank() && !userInfo.linkUnlocked) {
                    updateMediaFreeTimes(binding.ivLinkType, binding.tvLinkTimes)
                } else {
                    binding.ivUserLinkTitle.gone()
                    binding.flLinkLocked.gone()
                }
            }
            else -> {
                binding.tvContact.gone()
                binding.llCopy.gone()
                binding.tvNotLink.visible()
            }
        }
    }

    private fun setUserVideo(): Boolean {
        val userInfo = userInfoViewModel.getUserInfoModel()
        // 设置 Video 信息
        val userVideoUrl = userInfo.videoConvert()
        /*if (userVideoUrl.isNotEmpty()) {
            binding.tvVideo.visible()
            binding.flVideo.visible()
            VideoLoader.Builder()
                .request(userVideoUrl)
                .target(binding.ivVideo)
                .build()
        }*/
        return userVideoUrl.isBlank()
    }

    private fun setUserImage(): Boolean {
        val userInfo = userInfoViewModel.getUserInfoModel()
        val userImageList = userInfo.imageListConvert()
        when {
            userImageList.isNotEmpty() -> {
                val adUnitId = AdConfig.getAdvertiseUnitId(AdCodeKey.VIEW_USER_IMAGE)
                if (!Account.userSubscribe && adUnitId.isNotBlank() && !userInfo.imageUnlocked) {
                    binding.rvImage.visibility = View.GONE
                    updateMediaFreeTimes(binding.ivPhotoType, binding.tvPhotoTimes)
                } else {
                    binding.ivUserPhotoTitle.visible()
                    binding.flPhotoLocked.visible()
                    binding.rvImage.visible()
                }
                binding.rvImage.layoutManager = NoScrollGridLayoutManager(this, 2)
                binding.rvImage.addItemDecoration(SimpleGridDecoration(this))
                binding.rvImage.adapter = UserImageAdapter(userImageList) { position ->
                    FireBaseManager.logEvent(FirebaseKey.CLICK_PHOTO)
                    ImageMediaActivity
                        .create(this, userInfo.targetId, userInfo.targetIsBlacked)
                    LiveEventBus
                        .get<Pair<Int, MutableList<String>>>(KvKey.DISPLAY_IMAGE_MEDIA_LIST)
                        .post(Pair(position, userImageList))
                }
            }
            else -> {
                binding.llUserPhotoTitle.gone()
                binding.rvImage.gone()
            }
        }
        return userImageList.isEmpty()
    }

    private fun loadUserInfo() {
        binding.lvLoading.visible()
        userInfoViewModel.loadUserInfo(userId, DeviceManager.number)
    }

    override fun onMultipleClick(view: View) {
        super.onMultipleClick(view)
        when (view) {
            binding.ivBack -> finish()
            binding.ivReport -> reportDialog.show()
            binding.llFollow -> {
                if (Account.userLogged) {
                    loadingDialog.show()
                    userInfoViewModel.changeFollowedStatus()
                } else {
                    showToast(string(R.string.common_place_to_login_first))
                    LoginActivity.create(this)
                }
            }
            binding.llCopy -> {
                val label = string(R.string.app_name)
                val link = binding.tvContact.text.toString()
                val cmb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cmb.setPrimaryClip(ClipData.newPlainText(label, link))
                showToast(string(R.string.user_info_copy_success))
            }
            binding.flVideo -> {
                userInfoViewModel.getUserInfoModel().let {
                    VideoMediaActivity.create(
                        this,
                        it.videoConvert(),
                        it.targetId,
                        it.targetIsBlacked
                    )
                }
            }
            binding.clViewLink -> {
                val userInfo = userInfoViewModel.getUserInfoModel()
                when {
                    userInfo.remainUnlockCount <= 0 -> {
                        RechargeActivity.create(this)
                    }
                    userInfo.videoUnlocked || userInfo.imageUnlocked -> {
                        // 点击直接查看
                        if (userInfo.unlockMethod == 1) {
                            FireBaseManager.logEvent(FirebaseKey.CLICK_FREE_UNLOCK_CONTACT)
                            binding.ivUserLinkTitle.gone()
                            binding.flLinkLocked.gone()
                            userInfoViewModel.changeUnlockStatus(
                                DeviceManager.number,
                                UnlockType.Link.value,
                                userInfo.targetId
                            )
                        } else {
                            // 观看广告后可查看
                            FireBaseManager
                                .logEvent(FirebaseKey.CLICK_AD_TO_UNLOCK_THE_CONTACT_INFORMATION)
                            FireBaseManager
                                .logEvent(FirebaseKey.SHOW_CONTACT_POPUP_DOUBLE_CHECK)
                            viewAdDialog.show()
                            viewAdDialog.setOnConfirmRequest { loadLinkAd() }
                        }
                    }
                    else -> {
                        FireBaseManager
                            .logEvent(FirebaseKey.SHOW_FIRST_UNLOCK_VIDEO_OR_PICTURE)
                        viewLinkDialog.show()
                        viewLinkDialog.setOnConfirmRequest { loadImages() }
                    }
                }
            }
            binding.clViewPhoto -> loadImages()
            binding.tvMessage -> {
                FireBaseManager
                    .logEvent(FirebaseKey.CLICK_MESSAGE_ON_PROFILE_PAGE)
                if (Account.userLogged) {
                    userInfoViewModel.getUserInfoModel().let {
                        if (it.targetIsBlacked) return
                        ChatMessageActivity.create(this, it.targetId)
                    }
                } else {
                    showToast(string(R.string.common_place_to_login_first))
                    LoginActivity.create(this)
                }
            }
            binding.llNext -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_NEXT)
                create(this)
                finish()
            }
        }
    }

    /** 加载获取联系方式激励视频广告 */
    private fun loadLinkAd() {
        loadingDialog.show()
        val userInfo = userInfoViewModel.getUserInfoModel()
        userInfoViewModel.loadLinkAd(
            activity = this,
            failure = { loadingDialog.dismiss() },
            success = {
                loadingDialog.dismiss()
                binding.ivUserLinkTitle.gone()
                binding.flLinkLocked.gone()
                userInfoViewModel.changeUnlockStatus(
                    DeviceManager.number,
                    UnlockType.Link.value,
                    userInfo.targetId
                )
            }
        )
    }

    /** 获取个人相册插页广告 */
    private fun loadImages() {
        val userInfo = userInfoViewModel.getUserInfoModel()
        when {
            userInfo.remainUnlockCount <= 0 -> {
                RechargeActivity.create(this)
            }
            userInfo.unlockMethod == 1 -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_UNLOCK_PHOTO_ALBUM_FOR_FREE)
                binding.ivUserPhotoTitle.gone()
                binding.flPhotoLocked.gone()
                binding.rvImage.visible()
                userInfoViewModel.changeUnlockStatus(
                    DeviceManager.number,
                    UnlockType.Image.value,
                    userInfo.targetId
                )
            }
            else -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_AD_TO_UNLOCK_THE_ALBUM)
                loadingDialog.show()
                userInfoViewModel.loadImageAd(
                    activity = this,
                    failure = { loadingDialog.dismiss() },
                    success = {
                        binding.ivUserPhotoTitle.gone()
                        binding.flPhotoLocked.gone()
                        binding.rvImage.gone()
                        userInfoViewModel.changeUnlockStatus(
                            DeviceManager.number,
                            UnlockType.Image.value,
                            userInfo.targetId
                        )
                    })
            }
        }
    }

    companion object {
        private const val USER_ID = "USER_ID"
        internal fun create(context: Context, userId: Long = 0) {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(USER_ID, userId)
            context.startActivity(intent)
        }
    }
}