package com.leifu.mvpkotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseActivity : AppCompatActivity() {
//    var mContext: Context by Delegates.notNull()
//    var mActivity: AppCompatActivity by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mContext = this.applicationContext
//        mActivity = this
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData()
    }

    open fun initView(savedInstanceState: Bundle?) {
    }


    /**
     *  加载布局
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()
}