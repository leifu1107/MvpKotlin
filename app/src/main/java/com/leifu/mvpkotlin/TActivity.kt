package com.leifu.mvpkotlin

import com.leifu.mvpkotlin.base.BaseMvpActivity
import com.leifu.mvpkotlin.bean.TBean
import com.leifu.mvpkotlin.presenter.TPresenter
import com.leifu.mvpkotlin.presenter.contract.TContract
import kotlinx.android.synthetic.main.activity_observable.*

class TActivity : BaseMvpActivity<TContract.View, TPresenter>(), TContract.View {


    override fun getLayoutId(): Int = R.layout.activity_observable

    override fun initData() {
        startNetwork()
    }

    override fun startNetwork() {
        mPresenter?.getObjectData()
    }

    override fun createPresenter(): TPresenter {
        return TPresenter()
    }

    override fun showObjectData(objectBean: TBean) {
        textView.text =
            "toString()-----" + objectBean.toString() + "------appId---${objectBean.appId}"
    }
}
