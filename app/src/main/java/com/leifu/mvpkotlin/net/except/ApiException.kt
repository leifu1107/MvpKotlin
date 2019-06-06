package com.leifu.mvpkotlin.net.except

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:05
 *描述:
 */
class ApiException : Exception {
    private var code: Int? = null


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}