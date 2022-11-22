package com.milk.happymessage.account.data

import com.milk.happymessage.common.db.table.UserInfoEntity

data class FansOrFollowsModel(
    var current: Int = 0,
    var pages: Int = 0,
    var records: MutableList<UserInfoEntity>? = null,
    var total: Int = 0
)