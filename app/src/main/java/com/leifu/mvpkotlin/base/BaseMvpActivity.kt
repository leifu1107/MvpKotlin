package com.leifu.mvpkotlin.base

import android.os.Bundle
import android.widget.Toast
import com.leifu.mvpkotlin.view.LoadingUtil

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
        LoadingUtil.showLoading(mActivity, "加载中...")
    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
        LoadingUtil.dismissLoading()
    }

    /**
     * 显示错误提示
     */
    override fun showErrorMsg(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
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