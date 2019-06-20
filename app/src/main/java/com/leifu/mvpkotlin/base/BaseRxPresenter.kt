package com.leifu.mvpkotlin.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 *创建人:雷富
 *创建时间:2019/6/5 16:03
 *描述:
 */
open class BaseRxPresenter<V : IBaseView> : IBasePresenter<V> {
    var mView: V? = null
        private set

    private var compositeDisposable: CompositeDisposable? = null
    fun addSubscription(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        mView = null
        compositeDisposable?.run {
            if (!isDisposed) {
                clear()
            }
        }
    }
}