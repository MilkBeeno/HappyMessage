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
import com.milk.happymessage.account.ui.vm.FollowsViewModel
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.paging.SimpleGridDecoration
import com.milk.happymessage.common.paging.status.RefreshStatus
import com.milk.happymessage.common.ui.AbstractActivity
import com.milk.happymessage.databinding.ActivityFollowsBinding
import com.milk.happymessage.login.ui.dialog.LoadingDialog
import com.milk.happymessage.user.ui.act.UserInfoActivity
import com.milk.simple.ktx.*

class FollowsActivity : AbstractActivity() {
    private val binding by lazy { ActivityFollowsBinding.inflate(layoutInflater) }
    private val followsViewModel by viewModels<FollowsViewModel>()
    private val followsAdapter by lazy { FansOrFollowsAdapter() }
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
        initializeData()
    }

    private fun initializeView() {
        immersiveStatusBar()
        loadingDialog.show()
        binding.headerToolbar.statusBarPadding()
        binding.root.navigationBarPadding()
        binding.headerToolbar.showArrowBack()
        binding.headerToolbar.setTitle(R.string.mine_follows)
        binding.rvFollows.layoutManager = GridLayoutManager(this, 2)
        binding.rvFollows.addItemDecoration(SimpleGridDecoration(this))
        binding.rvFollows.adapter = followsAdapter
        binding.tvAttention.setOnClickListener(this)
        followsAdapter.setOnItemClickListener { adapter, _, position ->
            UserInfoActivity.create(this, adapter.getNoNullItem(position).targetId)
        }
    }

    private fun initializeData() {
        FireBaseManager.logEvent(FirebaseKey.FOLLOW_SHOW)
        followsAdapter.addRefreshedListener {
            loadingDialog.dismiss()
            if (it == RefreshStatus.Success && followsAdapter.itemCount > 0) {
                binding.llFollowsEmpty.gone()
            } else {
                binding.llFollowsEmpty.visible()
            }
        }
        followsViewModel.pagingSource.flow.collectLatest(this) {
            followsAdapter.submitData(it)
        }
    }

    override fun onMultipleClick(view: View) {
        super.onMultipleClick(view)
        when (view) {
            binding.tvAttention -> {
                finish()
                LiveEventBus.get<Any?>(EventKey.JUMP_TO_THE_HOME_PAGE).post(null)
            }
        }
    }

    companion object {
        internal fun create(context: Context) {
            context.startActivity(Intent(context, FollowsActivity::class.java))
        }
    }
}