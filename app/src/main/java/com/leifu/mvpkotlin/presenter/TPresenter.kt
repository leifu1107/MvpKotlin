package com.leifu.mvpkotlin.presenter

import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.bean.TBean
import com.leifu.mvpkotlin.net.BaseResponseBean
import com.leifu.mvpkotlin.net.FlowableSubscriberManager
import com.leifu.mvpkotlin.net.RetrofitManager
import com.leifu.mvpkotlin.net.RxManage
import com.leifu.mvpkotlin.presenter.contract.TContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class TPresenter : BaseRxPresenter<TContract.View>(), TContract.Presenter {


    override fun getObjectData() {
        addSubscription(
            RetrofitManager.apiService.getTData()
                .compose(RxManage.rxSchedulerFlowableHelper<BaseResponseBean<TBean>>())//需要加上这句转化
                .compose(RxManage.handleFlowableResult2())//通过泛型处理
                .subscribeWith(
                    object : FlowableSubscriberManager<TBean>(mView, true) {
                        override fun onNext(bean: TBean) {
                            this@TPresenter.mView?.showObjectData(bean)
                        }
                    })
        )
    }
}