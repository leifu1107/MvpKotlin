package com.leifu.mvpkotlin

import com.leifu.mvpkotlin.base.BaseMvpActivity
import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.presenter.FlowablePresenter
import com.leifu.mvpkotlin.presenter.contract.FlowableContract
import kotlinx.android.synthetic.main.activity_observable.*

class FlowableActivity : BaseMvpActivity<FlowableContract.View, FlowablePresenter>(), FlowableContract.View {


    override fun getLayoutId(): Int = R.layout.activity_observable

    override fun initData() {
        startNetwork()
    }

    override fun startNetwork() {
        mPresenter?.getObjectData()
    }

    override fun createPresenter(): FlowablePresenter {
        return FlowablePresenter()
    }

    override fun showObjectData(objectBean: ObjectBean) {
        textView.text =
            "toString()-----" + objectBean.toString() + "------code---${objectBean.code}" + "msg" + objectBean.msg
    }
}
