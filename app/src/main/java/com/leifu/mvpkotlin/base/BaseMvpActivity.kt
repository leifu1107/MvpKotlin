package com.leifu.mvpkotlin.base

import android.os.Bundle

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseMvpActivity<in V : IBaseView, P : IBasePresenter<V>> : BaseActivity(), IBaseView {

    var mPresenter: P? = null


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }

    /**
     * 请求网络数据
     */
    abstract fun startNetwork()

    abstract fun createPresenter(): P
}