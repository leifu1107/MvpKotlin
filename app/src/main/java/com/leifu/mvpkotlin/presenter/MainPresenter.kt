package com.leifu.mvpkotlin.presenter

import android.util.Log
import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.net.BaseBean
import com.leifu.mvpkotlin.net.RetrofitManager
import com.leifu.mvpkotlin.net.RxUtil
import com.leifu.mvpkotlin.net.except.ExceptionHandle
import com.leifu.mvpkotlin.presenter.contract.MainContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class MainPresenter : BaseRxPresenter<MainContract.View>(), MainContract.Presenter {


    override fun getCategoryData() {
        Log.e("okhttp", "getCategoryData")
        mView?.showLoading()
        addSubscription(
            RetrofitManager.apiService.getFirstHomeData()
                .compose(RxUtil.rxSchedulerObservableHelper())
                .compose(RxUtil.handleObservableResult<BaseBean>())
                .subscribe({
                    run { ->
                        mView?.showCategory(it)
                        mView?.dismissLoading()
                    }
                },
                    { t ->
                        ExceptionHandle.handleException(t)
                    })
        )
    }
}