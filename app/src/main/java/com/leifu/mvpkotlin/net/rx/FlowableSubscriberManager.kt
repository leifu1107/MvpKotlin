package com.leifu.mvpkotlin.net.rx


import com.leifu.mvpkotlin.base.IBaseView
import com.leifu.mvpkotlin.net.except.ExceptionHandle
import io.reactivex.subscribers.ResourceSubscriber

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述:
 */

abstract class FlowableSubscriberManager<T> : ResourceSubscriber<T> {
    var mView: IBaseView? = null
    /**
     * 是否显示加载中
     */
    private var isShowLoading = true

    constructor(mView: IBaseView?) {
        this.mView = mView
    }

    constructor(mView: IBaseView?, isShowLoading: Boolean) {
        this.mView = mView
        this.isShowLoading = isShowLoading
    }


    override fun onStart() {
        super.onStart()
        if (isShowLoading) {
            mView?.showLoading()
        }
    }

    override fun onNext(t: T) {
        mView?.showContent()
    }

    override fun onComplete() {
        if (isShowLoading) {
            mView?.dismissLoading()
        }
    }

    override fun onError(e: Throwable) {
        if (isShowLoading) {
            mView?.dismissLoading()
        }
        ExceptionHandle.handleException(e, mView)
    }
}
