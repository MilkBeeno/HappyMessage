package com.milk.happymessage.common.paging

interface MultiTypeDelegate {
    fun getItemViewId(viewType: Int): Int
}