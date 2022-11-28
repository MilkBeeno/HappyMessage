package com.milk.happymessage.account.ui.frag

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.milk.happymessage.R
import com.milk.happymessage.account.Account
import com.milk.happymessage.account.ui.act.*
import com.milk.happymessage.account.ui.dialog.LogoutDialog
import com.milk.happymessage.app.ui.MainViewModel
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.media.loader.ImageLoader
import com.milk.happymessage.common.ui.AbstractFragment
import com.milk.happymessage.databinding.FragmentMineBinding
import com.milk.happymessage.login.ui.act.GenderActivity
import com.milk.happymessage.login.ui.act.LoginActivity
import com.milk.happymessage.login.ui.dialog.LoadingDialog
import com.milk.happymessage.user.ui.config.AvatarImage
import com.milk.happymessage.user.ui.config.GenderImage
import com.milk.simple.ktx.collectLatest
import com.milk.simple.ktx.gone
import com.milk.simple.ktx.string
import com.milk.simple.ktx.visible

class MineFragment : AbstractFragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding by lazy { FragmentMineBinding.inflate(layoutInflater) }
    private val defaultAvatar by lazy { AvatarImage.obtain(Account.userGender) }
    private val logoutDialog by lazy { LogoutDialog(requireActivity()) }
    private val loadingDialog by lazy { LoadingDialog(requireActivity()) }

    override fun getRootView(): View = binding.root

    override fun initializeView() {
        binding.clFollows.setOnClickListener(this)
        binding.clFans.setOnClickListener(this)
        binding.tvEditProfile.setOnClickListener(this)
        binding.tvRecharge.setOnClickListener(this)
        binding.blackedList.setOnClickListener(this)
        binding.aboutUs.setOnClickListener(this)
        binding.signOut.setOnClickListener(this)
        binding.clNotSigned.setOnClickListener(this)
        binding.llLogin.setOnClickListener(this)
        binding.blackedList.setOption(R.drawable.mine_blacked_list, R.string.mine_blacked_list)
        binding.aboutUs.setOption(R.drawable.mine_about_us, R.string.mine_about_us)
        binding.signOut.setOption(R.drawable.mine_sign_out, R.string.mine_sign_out)
        logoutDialog.setOnConfirmListener {
            FireBaseManager.logEvent(FirebaseKey.LOG_OUT_SUCCESS)
            loadingDialog.show()
            Account.logout()
            mainViewModel.uploadDeviceInfo()
        }
    }

    override fun initializeObserver() {
        Account.userLoggedFlow.collectLatest(this) {
            if (it) {
                binding.clNotSigned.gone()
            } else {
                binding.clNotSigned.visible()
            }
        }
        Account.userAvatarFlow.collectLatest(this) {
            if (it.isNotBlank()) {
                ImageLoader.Builder().loadAvatar(it).target(binding.ivUserAvatar).build()
                ImageLoader.Builder()
                    .request(it)
                    .target(binding.ivAvatarLargest)
                    .build()
            } else {
                binding.ivUserAvatar.setImageResource(defaultAvatar)
            }
        }
        Account.userGenderFlow.collectLatest(this) {
            binding.ivUserGender.setImageResource(GenderImage.obtain(it))
        }
        Account.userNameFlow.collectLatest(this) {
            binding.tvUserName.text =
                it.ifBlank { requireActivity().string(R.string.mine_default_user_name) }
        }
        Account.userFansFlow.collectLatest(this) {
            binding.tvFans.text = it.toString()
        }
        Account.userFollowsFlow.collectLatest(this) {
            binding.tvFollows.text = it.toString()
        }
        mainViewModel.logOutFlow.collectLatest(this) {
            loadingDialog.dismiss()
            if (it) GenderActivity.create(requireActivity())
        }
    }

    override fun onMultipleClick(view: View) {
        super.onMultipleClick(view)
        when (view) {
            binding.clFollows -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_FOLLOW)
                FollowsActivity.create(requireContext())
            }
            binding.clFans -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_FAN)
                FansActivity.create(requireContext())
            }
            binding.tvEditProfile -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_ON_EDIT_PROFILE)
                EditProfileActivity.create(requireContext())
            }
            binding.tvRecharge -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_SUBSCRIPTION_PORTAL)
                RechargeActivity.create(requireContext())
            }
            binding.blackedList -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_BLACKLIST)
                BlackedListActivity.create(requireContext())
            }
            binding.aboutUs -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_ABOUT_OUR)
                AboutUsActivity.create(requireContext())
            }
            binding.signOut -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_THE_LOG_OUT)
                logoutDialog.show()
            }
            binding.llLogin -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_ON_MY_PAGE_LOGIN_PORTAL)
                LoginActivity.create(requireContext())
            }
        }
    }

    companion object {
        internal fun create(): Fragment {
            return MineFragment()
        }
    }
}