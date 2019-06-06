package com.leifu.mvpkotlin.presenter.contract

import com.leifu.mvpkotlin.base.IBasePresenter
import com.leifu.mvpkotlin.base.IBaseView
import com.leifu.mvpkotlin.net.BaseBean


/**
 *
 */
interface MainContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(baseBean: BaseBean)

    }

    interface Presenter : IBasePresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}