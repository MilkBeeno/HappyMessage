package com.milk.happymessage.common.ad.data

data class AdModel(
    val code: String = "",
    val positionList: MutableList<AdPositionModel>? = null
)