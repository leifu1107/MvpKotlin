package com.leifu.mvpkotlin.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.leifu.mvpkotlin.view.LoadingUtil
import com.leifu.mvpkotlin.view.MultipleStatusView

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseMvpFragment<in V : IBaseView, P : IBasePresenter<V>> : BaseFragment(), IBaseView {

    var mPresenter: P? = null

    /**
     * 多种状态的 View 的切换
     */
    var mLayoutStatusView: MultipleStatusView? = null

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
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
        mLayoutStatusView?.showLoading() ?: LoadingUtil.showLoading(mActivity, "加载中...")
    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
        LoadingUtil.dismissLoading()
    }


    /**
     * 无网络
     */
    override fun showNoNetwork() {
        mLayoutStatusView?.showNoNetwork()
    }

    /**
     * 显示内容视图
     */
    override fun showContent() {
        mLayoutStatusView?.showContent()
    }

    /**
     * 显示错误视图
     */
    override fun showError() {
        mLayoutStatusView?.showError()
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