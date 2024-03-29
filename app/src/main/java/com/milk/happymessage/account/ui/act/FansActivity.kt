package com.milk.happymessage.account.ui.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.R
import com.milk.happymessage.account.ui.adapter.FansOrFollowsAdapter
import com.milk.happymessage.account.ui.vm.FansViewModel
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.paging.SimpleGridDecoration
import com.milk.happymessage.common.paging.status.RefreshStatus
import com.milk.happymessage.common.ui.AbstractActivity
import com.milk.happymessage.databinding.ActivityFansBinding
import com.milk.happymessage.login.ui.dialog.LoadingDialog
import com.milk.happymessage.user.ui.act.UserInfoActivity
import com.milk.simple.ktx.*

class FansActivity : AbstractActivity() {
    private val binding by lazy { ActivityFansBinding.inflate(layoutInflater) }
    private val fansViewModel by viewModels<FansViewModel>()
    private val fansAdapter by lazy { FansOrFollowsAdapter() }
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
        initializeData()
    }

    private fun initializeView() {
        immersiveStatusBar()
        binding.headerToolbar.statusBarPadding()
        binding.root.navigationBarPadding()
        binding.headerToolbar.showArrowBack()
        binding.headerToolbar.setTitle(R.string.mine_fans)
        binding.rvFans.layoutManager = GridLayoutManager(this, 2)
        binding.rvFans.addItemDecoration(SimpleGridDecoration(this))
        binding.rvFans.adapter = fansAdapter
        binding.tvPublish.setOnClickListener(this)
        fansAdapter.setOnItemClickListener { adapter, _, position ->
            UserInfoActivity.create(this, adapter.getNoNullItem(position).targetId)
        }
    }

    private fun initializeData() {
        FireBaseManager.logEvent(FirebaseKey.FAN_SHOW)
        loadingDialog.show()
        fansAdapter.addRefreshedListener {
            loadingDialog.dismiss()
            if (it == RefreshStatus.Success && fansAdapter.itemCount > 0) {
                binding.llFanEmpty.gone()
            } else {
                binding.llFanEmpty.visible()
            }
        }
        fansViewModel.pagingSource.flow.collectLatest(this) {
            fansAdapter.submitData(it)
        }
    }

    override fun onMultipleClick(view: View) {
        super.onMultipleClick(view)
        when (view) {
            binding.tvPublish -> {
                FireBaseManager.logEvent(FirebaseKey.CLICK_FAN_AVATAR)
                LiveEventBus.get<Any?>(EventKey.JUMP_TO_THE_HOME_PAGE).post(null)
                finish()
            }
        }
    }

    companion object {
        internal fun create(context: Context) {
            context.startActivity(Intent(context, FansActivity::class.java))
        }
    }
}