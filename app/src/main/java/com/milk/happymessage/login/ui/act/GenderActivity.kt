package com.milk.happymessage.login.ui.act

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import com.milk.happymessage.R
import com.milk.happymessage.account.Account
import com.milk.happymessage.app.ui.act.MainActivity
import com.milk.happymessage.common.constrant.EventKey
import com.milk.happymessage.common.constrant.FirebaseKey
import com.milk.happymessage.common.firebase.FireBaseManager
import com.milk.happymessage.common.ui.AbstractActivity
import com.milk.happymessage.databinding.ActivityGenderBinding
import com.milk.happymessage.user.status.Gender
import com.milk.simple.ktx.*

class GenderActivity : AbstractActivity() {
    private val binding by lazy { ActivityGenderBinding.inflate(layoutInflater) }
    private var selectGender = Gender.Man

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        FireBaseManager
            .logEvent(FirebaseKey.OPEN_SELECT_GENDER_PAGE)
        immersiveStatusBar()
        binding.root.navigationBarPadding()
        updateManStatus(true)
        updateWomanStatus(false)
        binding.clMan.setOnClickListener(this)
        binding.clWoman.setOnClickListener(this)
        binding.tvGenderNext.setOnClickListener(this)
    }

    override fun onMultipleClick(view: View) {
        super.onMultipleClick(view)
        when (view) {
            binding.clMan -> {
                if (selectGender != Gender.Man) {
                    updateManStatus(true)
                    updateWomanStatus(false)
                    selectGender = Gender.Man
                }
            }
            binding.clWoman -> {
                if (selectGender != Gender.Woman) {
                    FireBaseManager.logEvent(FirebaseKey.CLICK_GIRL)
                    updateManStatus(false)
                    updateWomanStatus(true)
                    selectGender = Gender.Woman
                }
            }
            binding.tvGenderNext -> {
                Account.userGender = selectGender.value
                LiveEventBus.get<Boolean>(EventKey.REFRESH_HOME_LIST)
                    .post(false)
                MainActivity.create(this)
                finish()
            }
        }
    }

    private fun updateManStatus(select: Boolean) {
        if (select) {
            binding.clMan.setBackgroundResource(R.drawable.shape_gender_select_man_background)
            binding.ivMan.setImageResource(R.drawable.gender_man_select)
            binding.ivManSelected.visible()
            binding.tvMan.setTextColor(color(R.color.white))
            binding.tvMan.typeface = Typeface.DEFAULT_BOLD
            binding.tvMan.textSize = 17f
            val params = binding.tvMan.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(-dp2px(12f).toInt(), 0, 0, 0)
            binding.tvMan.layoutParams = params
        } else {
            binding.clMan.setBackgroundResource(R.drawable.shape_gender_select_background)
            binding.ivMan.setImageResource(R.drawable.gender_man)
            binding.ivManSelected.gone()
            binding.tvMan.setTextColor(color(R.color.black))
            binding.tvMan.typeface = Typeface.DEFAULT
            binding.tvMan.textSize = 14f
            val params = binding.tvMan.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(dp2px(2f).toInt(), 0, 0, 0)
            binding.tvMan.layoutParams = params
        }
    }

    private fun updateWomanStatus(select: Boolean) {
        if (select) {
            binding.clWoman.setBackgroundResource(R.drawable.shape_gender_select_woman_background)
            binding.ivWoman.setImageResource(R.drawable.gender_woman_select)
            binding.ivWomanSelected.visible()
            binding.tvWoman.setTextColor(color(R.color.white))
            binding.tvWoman.typeface = Typeface.DEFAULT_BOLD
            binding.tvWoman.textSize = 17f
            val params = binding.tvWoman.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(0, 0, -dp2px(12f).toInt(), 0)
            binding.tvWoman.layoutParams = params
        } else {
            binding.clWoman.setBackgroundResource(R.drawable.shape_gender_select_background)
            binding.ivWoman.setImageResource(R.drawable.gender_woman)
            binding.ivWomanSelected.gone()
            binding.tvWoman.setTextColor(color(R.color.black))
            binding.tvWoman.typeface = Typeface.DEFAULT
            binding.tvWoman.textSize = 14f
            val params = binding.tvWoman.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(0, 0, 0, dp2px(4f).toInt())
            binding.tvWoman.layoutParams = params
        }
    }

    override fun onInterceptKeyDownEvent() = true

    companion object {
        internal fun create(context: Context) =
            context.startActivity(Intent(context, GenderActivity::class.java))
    }
}