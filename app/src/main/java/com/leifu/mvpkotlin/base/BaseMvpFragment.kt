package com.leifu.mvpkotlin.base

import android.os.Bundle
import android.view.View

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseMvpFragment<in V : IBaseView, P : IBasePresenter<V>> : BaseFragment(), IBaseView {

    var mPresenter: P? = null


    override fun initView(view: View?, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }


    /**
     * 加载中
     */
    override fun showLoading() {

    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
    }

    /**
     * 请求网络数据
     */
    abstract fun startNetwork()

    /**
     * 创建Presenter
     */
    abstract fun createPresenter(): P
}