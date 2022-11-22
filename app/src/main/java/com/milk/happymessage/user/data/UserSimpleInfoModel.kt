package com.milk.happymessage.user.data

import com.milk.happymessage.common.db.table.UserInfoEntity
import com.milk.happymessage.user.status.ItemAdType

/** @param isMediumImage: 本地用来判断是否是小图 */
data class UserSimpleInfoModel(
    var isMediumImage: Boolean = false,
    var itemAdType: ItemAdType = ItemAdType.Null
) : UserInfoEntity()