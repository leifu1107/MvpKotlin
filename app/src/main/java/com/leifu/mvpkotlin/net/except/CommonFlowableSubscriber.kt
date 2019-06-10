package com.leifu.mvpkotlin.net.except


import io.reactivex.subscribers.ResourceSubscriber

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述:
 */

abstract class CommonFlowableSubscriber<T> : ResourceSubscriber<T>() {

    //自己加的,防止出现问题,测试
    override fun onStart() {
        super.onStart()
    }

    override fun onComplete() {}

    override fun onError(e: Throwable) {
        ExceptionHandle.handleException(e)
    }
}
