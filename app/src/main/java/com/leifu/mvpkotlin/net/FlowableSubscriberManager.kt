package com.leifu.mvpkotlin.net


import com.leifu.mvpkotlin.net.except.ExceptionHandle
import io.reactivex.subscribers.ResourceSubscriber

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述:
 */

abstract class FlowableSubscriberManager<T> : ResourceSubscriber<T>() {

    override fun onStart() {
        super.onStart()
    }

    override fun onComplete() {}

    override fun onError(e: Throwable) {
        ExceptionHandle.handleException(e)
    }
}
