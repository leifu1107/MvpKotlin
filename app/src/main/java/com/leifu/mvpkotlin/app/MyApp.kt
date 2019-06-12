package com.leifu.mvpkotlin.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 *创建人:雷富
 *创建时间:2019/6/5 14:24
 *描述:
 */
class MyApp : Application() {
    private val TAG = "MyApplication"

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
    }

    private fun initConfig() {
    }
}