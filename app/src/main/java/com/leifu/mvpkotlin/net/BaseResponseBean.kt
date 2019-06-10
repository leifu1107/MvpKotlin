package com.leifu.mvpkotlin.net

/**
 * 封装返回的数据
 *
 */
class BaseResponseBean<T>(
    val code: Int,
    val msg: String,
    val data: T
)