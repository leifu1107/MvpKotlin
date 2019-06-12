package com.leifu.mvpkotlin.ui

import com.leifu.mvpkotlin.R
import com.leifu.mvpkotlin.base.BaseMvpActivity
import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.presenter.ObservablePresenter
import com.leifu.mvpkotlin.presenter.contract.ObservableContract
import kotlinx.android.synthetic.main.activity_observable.*

class ObservableActivity : BaseMvpActivity<ObservableContract.View, ObservablePresenter>(), ObservableContract.View {


    override fun getLayoutId(): Int = R.layout.activity_observable

    override fun initData() {
        startNetwork()
    }

    override fun startNetwork() {
        mPresenter?.getObjectData()
    }

    override fun createPresenter(): ObservablePresenter {
        return ObservablePresenter()
    }

    override fun showObjectData(objectBean: ObjectBean) {
        textView.text =
            "toString()-----" + objectBean.toString() + "------code---${objectBean.code}" + "msg" + objectBean.msg
    }
}
