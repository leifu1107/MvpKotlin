package com.leifu.mvpkotlin.presenter

import android.util.Log
import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.net.BaseBean
import com.leifu.mvpkotlin.net.RetrofitManager
import com.leifu.mvpkotlin.net.RxUtil
import com.leifu.mvpkotlin.net.except.CommonSubscriber
import com.leifu.mvpkotlin.presenter.contract.MainContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class MainPresenter : BaseRxPresenter<MainContract.View>(), MainContract.Presenter {


    override fun getCategoryData() {
        Log.e("okhttp", "getCategoryData")
        addSubscription(
            RetrofitManager.apiService.getFirstHomeData()
                .compose(RxUtil.rxSchedulerHelper<BaseBean>())
                .compose(RxUtil.handleResult1<BaseBean>())
                .subscribeWith(object : CommonSubscriber<BaseBean>() {
                    override fun onNext(t: BaseBean?) {
                    }
                })
        )
    }

}