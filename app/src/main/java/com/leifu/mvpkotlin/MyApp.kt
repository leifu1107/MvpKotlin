package com.leifu.mvpkotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
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
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    private fun initConfig() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivityResumed(p0: Activity?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivityStarted(p0: Activity?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivityDestroyed(p0: Activity?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivityStopped(p0: Activity?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onActivityCreated(p0: Activity, p1: Bundle) {
            Log.d(TAG, "onCreated: " + p0.componentName.className)
        }

    }
}