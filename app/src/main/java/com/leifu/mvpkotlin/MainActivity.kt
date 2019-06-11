package com.leifu.mvpkotlin

import android.content.Intent
import com.leifu.mvpkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        btnObservable.setOnClickListener { startActivity(Intent(mContext, ObservableActivity::class.java)) }
    }


}
