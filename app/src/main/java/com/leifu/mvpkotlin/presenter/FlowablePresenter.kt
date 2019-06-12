package com.leifu.mvpkotlin.presenter

import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.net.rx.FlowableSubscriberManager
import com.leifu.mvpkotlin.net.rx.RetrofitManager
import com.leifu.mvpkotlin.net.rx.RxManage
import com.leifu.mvpkotlin.presenter.contract.FlowableContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class FlowablePresenter : BaseRxPresenter<FlowableContract.View>(), FlowableContract.Presenter {


    override fun getObjectData() {
//        mView?.showLoading() 不用处理加载中和取消加载,通过true false控制
        addSubscription(
            RetrofitManager.apiService.getFlowableObjectData()
                .compose(RxManage.rxSchedulerFlowableHelper())
                .compose(RxManage.handleFlowableResult<ObjectBean>())
                .subscribeWith(
                    object : FlowableSubscriberManager<ObjectBean>(mView, true) {
                        override fun onNext(bean: ObjectBean) {
                            this@FlowablePresenter.mView?.showObjectData(bean)
                        }
                    })
//                .subscribe({
//                    run { ->
//                        val b = it is ObjectBean
//                        val c = it is BaseBean
//                        Log.e("ok", it.data.appId + it.code + b + c)
//                        mView?.showObjectData(it)
//                        mView?.dismissLoading()
//                    }
//                },
//                    { t ->
//                        mView?.dismissLoading()
//                        mView?.let { ExceptionHandle.handleException(t, it) }
//                    })
        )
    }
}