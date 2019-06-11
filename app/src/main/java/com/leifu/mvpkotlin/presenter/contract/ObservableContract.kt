package com.leifu.mvpkotlin.presenter.contract

import com.leifu.mvpkotlin.base.IBasePresenter
import com.leifu.mvpkotlin.base.IBaseView
import com.leifu.mvpkotlin.bean.ObjectBean


/**
 *
 */
interface ObservableContract {

    interface View : IBaseView {
        /**
         * 显示创建应用接口
         */
        fun showObjectData(objectBean: ObjectBean)

    }

    interface Presenter : IBasePresenter<View> {
        /**
         * 获取创建应用接口
         */
        fun getObjectData()
    }
}