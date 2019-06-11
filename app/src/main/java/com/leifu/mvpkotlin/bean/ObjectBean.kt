package com.leifu.mvpkotlin.bean

import com.leifu.mvpkotlin.net.BaseBean

/**
 *创建人:雷富
 *创建时间:2019/6/11 18:47
 *描述:
 */
data class ObjectBean(
    val appId: String,
    val appkey: String
) : BaseBean() {}