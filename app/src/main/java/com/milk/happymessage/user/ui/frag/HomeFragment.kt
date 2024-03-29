package com.milk.happymessage.user.ui.frag

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.R
import com.milk.happymessage.common.ad.AdConfig
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.constrant.KvKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.paging.StaggeredGridDecoration
import com.milk.happymessage.common.paging.status.RefreshStatus
import com.milk.happymessage.common.ui.AbstractFragment
import com.milk.happymessage.databinding.FragmentHomeBinding
import com.milk.happymessage.login.ui.dialog.LoadingDialog
import com.milk.happymessage.user.ui.act.UserInfoActivity
import com.milk.happymessage.user.ui.adapter.HomeAdapter
import com.milk.happymessage.user.ui.vm.HomeViewModel
import com.milk.simple.ktx.*
import com.milk.simple.mdr.KvManger

class HomeFragment : AbstractFragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { HomeAdapter() }
    private val loadingDialog by lazy { LoadingDialog(requireActivity()) }

    override fun getRootView(): View = binding.root

    @SuppressLint("NotifyDataSetChanged")
    override fun initializeData() {
        super.initializeData()
        checkNewClientOnHome()
        loadingDialog.show()
        adapter.addRefreshedListener {
            loadingDialog.dismiss()
            binding.refresh.finishRefresh(1500)
            if (adapter.itemCount > 0)
                binding.rvHome.scrollToPosition(0)
            when (it) {
                RefreshStatus.Success -> {
                    binding.homeNothing.root.gone()
                }
                else -> {
                    if (adapter.itemCount > 0) {
                        binding.homeNothing.root.gone()
                        requireContext().showToast(
                            requireContext().string(R.string.home_list_refresh_failed)
                        )
                    } else binding.homeNothing.root.visible()
                }
            }
        }
        homeViewModel.pagingSource.flow
            .collectLatest(this) { adapter.submitData(it) }
    }

    private fun checkNewClientOnHome() {
        val checkNewClientOnHome =
            KvManger.getBoolean(KvKey.CHECK_NEW_CLIENT_ON_HOME, true)
        if (checkNewClientOnHome) {
            KvManger.put(KvKey.CHECK_NEW_CLIENT_ON_HOME, false)
            FireBaseManager.logEvent(FirebaseKey.FIRST_OPEN_HOME_PAGE)
        }
    }

    override fun initializeObserver() {
        super.initializeObserver()
        LiveEventBus.get<Boolean>(EventKey.REFRESH_HOME_LIST)
            .observe(this) {
                if (adapter.itemCount > 0)
                    binding.rvHome.smoothScrollToPosition(0)
                binding.refresh.autoRefresh()
            }
    }

    override fun initializeView() {
        binding.clHeaderToolbar.statusBarPadding()
        binding.rvHome.itemAnimator = null
        binding.rvHome.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvHome.addItemDecoration(
            StaggeredGridDecoration(requireContext(), 10, 4)
        )
        binding.rvHome.adapter = adapter.withLoadStateFooterAdapter()
        binding.refresh.setRefreshHeader(binding.refreshHeader)
        binding.refresh.setOnRefreshListener {
            if (!AdConfig.isLoadedAds()) AdConfig.obtain()
            adapter.refresh()
        }
        adapter.setOnItemClickListener { adapter, _, position ->
            val user = adapter.getNoNullItem(position)
            if (user.targetId > 0) UserInfoActivity.create(requireContext(), user.targetId)
        }
    }

    companion object {
        internal fun create() = HomeFragment()
    }
}