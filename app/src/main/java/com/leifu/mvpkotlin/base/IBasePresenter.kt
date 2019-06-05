package com.leifu.mvpkotlin.base

/**
 *创建人:雷富
 *创建时间:2019/6/5 15:17
 *描述:
 */
interface IBasePresenter<in V : IBaseView> {

    fun attachView(view: V)

    fun detachView()
}