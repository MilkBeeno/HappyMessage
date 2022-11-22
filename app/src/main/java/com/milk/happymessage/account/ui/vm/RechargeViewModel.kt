package com.milk.happymessage.account.ui.vm

import androidx.lifecycle.ViewModel
import com.milk.happymessage.common.pay.PayManager

class RechargeViewModel : ViewModel() {
    internal fun salesOrder(productId: String, purchaseToken: String) {
        PayManager.getPayStatus(productId, purchaseToken)
    }
}