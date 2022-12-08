package com.milk.happymessage.common.constrant

object FirebaseKey {
    /** 登录处埋点 */
    const val FIRST_OPEN = "first_open"
    const val FIRST_OPEN_HOME_PAGE = "first_open_homepage"
    const val LOGINS_WITH_GUEST = "logins_guest"
    const val LOGINS_WITH_GUEST_SUCCESS = "logins_guest_Success"
    const val LOGINS_WITH_GUEST_FAIL = "logins_guest_fail"
    const val LOGINS_WITH_GOOGLE = "logins_Google"
    const val LOGINS_WITH_GOOGLE_SUCCESS = "logins_Google_Success"
    const val LOGINS_WITH_GOOGLE_FAIL = "logins_Google_fail"
    const val LOGINS_WITH_FB = "logins_fb"
    const val LOGINS_WITH_FB_SUCCESS = "logins_fb_Success"
    const val LOGINS_WITH_FB_FAIL = "logins_fb_fail"
    const val MAX_REGISTRATIONS_REACHED_SHOW = "max_registrations_show"
    const val CLICK_LOGIN_PAGE_PRIVACY_POLICY = "Click_Login_Privacy_Policy"
    const val CLICK_LOGIN_PAGE_USER_AGREEMENT = "Click_login_User_Agreement"
    const val LOGIN_SUCCESSFUL = "login_success"
    const val LOGIN_FAIL = "login_faill"

    /** 选择性别埋点 */
    const val OPEN_SELECT_GENDER_PAGE = "Open_Gender_page"
    const val CLICK_GIRL = "Click_the_girl"

    /** 填写基本资料页面 */
    const val OPEN_FILL_IN_THE_INFORMATION_PAGE = "Open_fill_in_information_page"
    const val CHANGE_NAME = "change_the_name"
    const val CLICK_DEFAULT_AVATAR = "Click_default_the_avatar"

    /** 我的页面埋点 */
    const val CLICK_MY_BUTTON = "Click_my_the_button"
    const val CLICK_ON_MY_PAGE_LOGIN_PORTAL = "Click_on_My_Login_Portal"
    const val CLICK_THE_LOG_OUT = "Click_log_out"
    const val LOG_OUT_SUCCESS = "log_the_out_Success"
    const val CLICK_BLACKLIST = "Click_the_blacklist"
    const val CLICK_THE_FOLLOW = "Click_Follow"
    const val CLICK_ABOUT_OUR = "Click_about_the_our"
    const val CLICK_THE_FAN = "Click_fan"
    const val CLICK_ON_EDIT_PROFILE = "Click_edit_profile"

    /** 黑名单、粉丝、关注埋点 */
    const val BLACKLIST_SHOW = "the_blacklist_show"
    const val FOLLOW_SHOW = "the_Follow_show"
    const val FAN_SHOW = "the_fan_show"
    const val CLICK_FAN_AVATAR = "the_Click_fan_avatar"

    /** 关于我们页面埋点 */
    const val ABOUT_OUR_SHOW = "the_about_our_show"
    const val CLICK_USER_AGREEMENT = "Click_the_User_Agreement"
    const val OPEN_USER_AGREEMENT_PAGE = "Open_User_Agreement"
    const val CLICK_PRIVACY_POLICY = "Click_the_Privacy_Policy"
    const val OPEN_AGREEMENT_PAGE = "Open_the_agreement_page"

    /** 编辑个人资料埋点 */
    const val OPEN_EDIT_PAGE = "Open_the_edit_page"
    const val CLICK_ON_THE_NICKNAME_BOX = "Click_on_Nickname_box"
    const val CLICK_ON_AVATAR = "Click_avatar"
    const val CLICK_ON_THE_CONTACTION = "Click_on_contaction"
    const val CLICK_ON_PERSONAL_INTRODUCTION = "Click_on_the_personal_introduction"
    const val CLICK_UPLOAD_IMAGE_ICON = "Click_the_upload_image_icon"
    const val UPLOAD_IMAGE_FAIL = "upload_the_image_fail"

    /** 消息列表页面埋点 */
    const val CLICK_CHAT_WITH_OTHER = "Click_chat_other"
    const val ENTER_MESSAGE = "enter_the_message"
    const val CLICK_THE_STICKY = "Click_sticky"
    const val CLICK_THE_DELETE = "Click_the_System_Messages"
    const val CLICK_TO_UNPIN = "Click_to_the_unpin"

    /** 对话页面埋点 */
    const val CLICK_TOP_ON_CHAT_PAGE = "Click_top_on_the_chat"
    const val CLICK_UNPIN__ON_CHAT_PAGE = "Click_Unpin__on_the_chat"
    const val CLICK_BLACKOUT_ON_CHAT_PAGE = "Click_blackout_on_the_chat"
    const val CLICK_FOLLOW_ON_CHAT_PAGE = "Click_Follow_on_the_chat"

    /** 个人主页埋点 */
    const val CLICK_MESSAGE_ON_PROFILE_PAGE = "Click_Message_on_the_Profile"
    const val CLICK_MESSAGE_VIEW_IMAGE_PAGE = "Click_Message_View_Image_the_Page"
    const val CLICK_MESSAGE_VIEW_VIDEO_PAGE = "Click_Message_the_View_video_Page"
    const val CLICK_THE_NEXT = "click_next"
    const val CLICK_PHOTO = "click_the_photo"
    const val SHOW_CONTACT_POPUP_DOUBLE_CHECK = "Show_Contact_the_Popup_Double_Check"
    const val CLICK_CONFIRM_CONTACT_DOUBLE_CHECK = "Click_Confirm_Contact_DoubleCheck"
    const val CLICK_CANCEL_SHOW_CONTACT_DOUBLE_CHECK = "Click_Cancel_Show_Contact_DoubleCheck"
    const val SHOW_FIRST_UNLOCK_VIDEO_OR_PICTURE = "Show_first_unlock_the_video_or_picture"

    /** 发起广告请求的次数_app启动插页广告 */
    const val MAKE_AN_AD_REQUEST = "Ad_requests"

    /** 广告请求成功_app启动插页广告 */
    const val AD_REQUEST_SUCCEEDED = "Ad_request_succeed"

    /** 广告请求失败（需要统计原因）_app启动插页广告 */
    const val AD_REQUEST_FAILED = "Ad_request_fail"

    /** 广告展示成功_app启动插页广告 */
    const val THE_AD_SHOW_SUCCESS = "The_ad_show_successfully"

    /** 广告展示失败（需要统计原因）_app启动插页广告 */
    const val AD_SHOW_FAILED = "Ad_show_fail"

    /** 点击广告位_app启动插页广告 */
    const val CLICK_AD = "click_the_ad"

    /** 发起广告请求_首页瀑布流原生广告 */
    const val MAKE_AN_AD_REQUEST_1 = "Ad_requests_1"

    /** 广告请求成功_首页瀑布流原生广告 */
    const val AD_REQUEST_SUCCEEDED_1 = "Ad_request_succeed_1"

    /** 广告请求失败（需要统计原因）_首页瀑布流原生广告 */
    const val AD_REQUEST_FAILED_1 = "Ad_request_fail_1"

    /** 广告展示成功_首页瀑布流原生广告 */
    const val THE_AD_SHOW_SUCCESS_1 = "The_ad_show_successfully_1"

    /** 广告展示失败（需要统计原因）_首页瀑布流原生广告 */
    const val AD_SHOW_FAILED_1 = "Ad_show_fail_1"

    /** 点击广告位_首页瀑布流原生广告 */
    const val CLICK_AD_1 = "click_the_ad_1"

    /** 发起广告请求_首页瀑布流原生广告-2 */
    const val MAKE_AN_AD_REQUEST_2 = "Ad_requests_2"

    /** 广告请求成功_首页瀑布流原生广告-2 */
    const val AD_REQUEST_SUCCEEDED_2 = "Ad_request_succeed_2"

    /** 广告请求失败（需要统计原因）_首页瀑布流原生广告-2 */
    const val AD_REQUEST_FAILED_2 = "Ad_request_fail_2"

    /** 广告展示成功_首页瀑布流原生广告-2 */
    const val THE_AD_SHOW_SUCCESS_2 = "The_ad_show_successfully_2"

    /** 广告展示失败（需要统计原因）_首页瀑布流原生广告-2 */
    const val AD_SHOW_FAILED_2 = "Ad_show_fail_2"

    /** 点击广告位_首页瀑布流原生广告-2 */
    const val CLICK_AD_2 = "click_the_ad_2"

    /** 发起广告请求_首页瀑布流原生广告-3 */
    const val MAKE_AN_AD_REQUEST_3 = "Ad_requests_3"

    /** 广告请求成功_首页瀑布流原生广告-3 */
    const val AD_REQUEST_SUCCEEDED_3 = "Ad_request_succeed_3"

    /** 广告请求失败（需要统计原因）_首页瀑布流原生广告-3 */
    const val Ad_request_failed_3 = "Ad_request_fail_3"

    /** 广告展示成功_首页瀑布流原生广告-3 */
    const val THE_AD_SHOW_SUCCESS_3 = "The_ad_show_successfully_3"

    /** 广告展示失败（需要统计原因）_首页瀑布流原生广告-3 */
    const val AD_SHOW_FAILED_3 = "Ad_show_fail_3"

    /** 点击广告位_首页瀑布流原生广告-3 */
    const val CLICK_AD_3 = "click_the_ad_3"

    /** 发起广告请求_首页底部的横幅广告 */
    const val MAKE_AN_AD_REQUEST_4 = "Ad_requests_4"

    /** 广告请求成功_首页底部的横幅广告 */
    const val AD_REQUEST_SUCCEEDED_4 = "Ad_request_succeed_4"

    /** 广告请求失败（需要统计原因）_首页底部的横幅广告 */
    const val AD_REQUEST_FAILED_4 = "Ad_request_failed_4"

    /** 广告展示成功_首页底部的横幅广告 */
    const val THE_AD_SHOW_SUCCESS_4 = "Ad_request_fail_4"

    /** 广告展示失败（需要统计原因）_首页底部的横幅广告 */
    const val AD_SHOW_FAILED_4 = "The_ad_show_successfully_4"

    /** 点击广告位_首页底部的横幅广告 */
    const val CLICK_AD_4 = "Ad_show_fail_4"

    /** 发起广告请求_个人主页查看更多照片插页广告 */
    const val MAKE_AN_AD_REQUEST_5 = "Ad_requests_5"

    /** 广告请求成功_个人主页查看更多照片插页广告 */
    const val AD_REQUEST_SUCCEEDED_5 = "Ad_request_succeed_5"

    /** 广告请求失败（需要统计原因）_个人主页查看更多照片插页广告 */
    const val AD_REQUEST_FAILED_5 = "Ad_request_failed_5"

    /** 广告展示成功_个人主页查看更多照片插页广告 */
    const val THE_AD_SHOW_SUCCESS_5 = "The_ad_show_successfully_5"

    /** 广告展示失败（需要统计原因）_个人主页查看更多照片插页广告 */
    const val AD_SHOW_FAILED_5 = "Ad_show_fail_5"

    /** 点击广告_个人主页查看更多照片插页广告 */
    const val CLICK_AD_5 = "click_the_ad_5"

    /** 发起广告请求_个人主页查看联系方式激励视频 */
    const val MAKE_AN_AD_REQUEST_6 = "Ad_requests_6"

    /** 广告请求成功_个人主页查看联系方式激励视频 */
    const val AD_REQUEST_SUCCEEDED_6 = "Ad_request_succeed_6"

    /** 广告请求失败（需要统计原因）_个人主页查看联系方式激励视频 */
    const val AD_REQUEST_FAILED_6 = "Ad_request_fail_6"

    /** 广告展示成功_个人主页查看联系方式激励视频 */
    const val THE_AD_SHOW_SUCCESS_6 = "The_ad_show_successfully_6"

    /** 广告展示失败（需要统计原因）_个人主页查看联系方式激励视频 */
    const val AD_SHOW_FAILED_6 = "Ad_show_fail_6"

    /** 点击广告_个人主页查看联系方式激励视频 */
    const val CLICK_AD_6 = "click_the_ad_6"

    /** 进入登录页面 */
    const val OPEN_LOGINS_PAGE = "open_logins_page"

    /** 发起广告请求的次数_订阅页面原生广告 */
    const val MAKE_AN_AD_REQUEST_7 = "Ad_requests_7"

    /** 广告请求成功的次数_订阅页面原生广告 */
    const val AD_REQUEST_SUCCEEDED_7 = "Ad_request_succeed_7"

    /** 广告请求失败的次数_订阅页面原生广告 */
    const val AD_REQUEST_FAILED_7 = "Ad_request_fail_7"

    /**  广告请求成功的次数_订阅页面原生广告 */
    const val THE_AD_SHOW_SUCCESS_7 = "The_ad_show_successfully_7"

    /** 广告展示失败的次数_订阅页面原生广告 */
    const val AD_SHOW_FAILED_7 = "Ad_show_fail_7"

    /** 点击广告次数_订阅页面原生广告 */
    const val CLICK_AD_7 = "click_the_ad_7"

    /** 点击免费解锁按钮进行解锁联系方式 */
    const val CLICK_FREE_UNLOCK_CONTACT = "Click_the_Free_Unlock_Contact"

    /** 点击广告解锁按钮进行解锁联系方式 */
    const val CLICK_AD_TO_UNLOCK_THE_CONTACT_INFORMATION =
        "click_the_ad_to_unlock_contact_information"

    /** 点击免费解锁按钮进行解锁相册 */
    const val CLICK_UNLOCK_PHOTO_ALBUM_FOR_FREE = "Click_unlock_photo_album_free"

    /** 点击广告解锁按钮进行解锁相册 */
    const val CLICK_THE_AD_TO_UNLOCK_THE_ALBUM = "Click_the_ad_to_unlock_album"

    /** 点击我的页面的订阅入口 */
    const val CLICK_THE_SUBSCRIPTION_PORTAL = "Click_subscription_portal"

    /** 用户进入到订阅页面 */
    const val SUBSCRIPTIONS_PAGE_SHOW = "the_Subscriptions_page_Show"

    /** 用户选择使用按周订阅 */
    const val CLICK_SUBSCRIBE_BY_WEEK = "Click_the_subscribe_week"

    /** 用户选择使用按年订阅 */
    const val CLICK_SUBSCRIBE_BY_YEAR = "Click_the_Subscribe_Year"

    /** 用户订阅成功提示 */
    const val SUBSCRIPTION_SUCCESS_SHOW = "Subscription_the_Success_Show"

    /** 用户已经订阅后重复点击 */
    const val SUBSCRIPTION_ALERTS_SHOW = "Subscription_the_Alerts_Show"

    /** 用户点击率push后进入到某个女用户的个人主页，这里需要注意用户来源 */
    const val CLICK_PROMOTE_PUSH = "Click_the_Promote_push"

    /** 进入他人个人页面后弹出的申请开启个人主页的 */
    const val OPEN_NOTIFICATION_REQUEST_POPUP_SHOW = "Open_notification_request_Show"

    /** 点击 开启通知弹窗 关闭按钮 */
    const val CLICK_OPEN_NOTIFICATION_POPUP_CANCEL = "Click_open_notification_cancel"

    /** 点击 开启通知弹窗 确认按钮 */
    const val CLICK_OPEN_NOTIFICATION_POPUP_CONFIRM = "Click_open_notification_Confirm"
}
