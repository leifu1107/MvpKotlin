package com.leifu.mvpkotlin.base

import io.reactivex.disposables.CompositeDisposable


/**
 *创建人:雷富
 *创建时间:2019/6/5 16:03
 *描述:
 */
open class BasePresenter<T : IBaseView> : IBasePresenter<T> {
    var view: T? = null
        private set

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}