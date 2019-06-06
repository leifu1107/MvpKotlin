package com.leifu.mvpkotlin

import android.util.Log
import com.leifu.mvpkotlin.base.BaseMvpActivity
import com.leifu.mvpkotlin.net.BaseBean
import com.leifu.mvpkotlin.presenter.MainPresenter
import com.leifu.mvpkotlin.presenter.contract.MainContract

class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override fun showCategory(baseBean: BaseBean) {
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        Log.e("okhttp", "initData")
        startNetwork()
    }

    override fun startNetwork() {
        Log.e("okhttp", "startNetwork")
        mPresenter?.getCategoryData()
    }

    override fun createPresenter(): MainPresenter {
        Log.e("okhttp", "createPresenter")
        return MainPresenter()
    }

}
