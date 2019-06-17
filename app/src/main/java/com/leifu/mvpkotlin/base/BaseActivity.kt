package com.leifu.mvpkotlin.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leifu.mvpkotlin.util.ActivityManager

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.applicationContext
        mActivity = this
        ActivityManager.instance.addActivity(this)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData()
        initListener()
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance.removeActivity(this)
    }

    /**
     *  加载布局
     */
    abstract fun getLayoutId(): Int

    /**
     *  initView
     */
    open fun initView(savedInstanceState: Bundle?) {
    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    open fun initListener() {
    }
}