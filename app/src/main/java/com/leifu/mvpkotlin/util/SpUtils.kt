package com.lsky.ener.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.leifu.mvpkotlin.app.MyApp


/**
 * 创建人: 雷富
 * 创建时间: 2018/1/29 15:11
 * 描述:本地存储
 */

object SpUtils {
    private val sp_name = "PF_CACHE"

    val appSp: SharedPreferences
        get() = MyApp.context.getSharedPreferences(sp_name, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        appSp.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        appSp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, value: Int): Int {
        return appSp.getInt(key, value)
    }

    fun getString(key: String, defValue: String): String? {
        return appSp.getString(key, defValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        appSp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return appSp.getBoolean(key, defValue)
    }

    fun putLong(key: String, value: Long) {
        appSp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return appSp.getLong(key, defValue)
    }

    fun remove(key: String) {
        appSp.edit().remove(key).apply()
    }

    /**
     * 移除所有数据
     */
    fun removeAll() {
        val sp = MyApp.context.getSharedPreferences(sp_name, Activity.MODE_PRIVATE)
        sp.edit().clear().apply()
    }

    /**
     * 移除用户所有数据,防止切换用户数据未更新
     */
    fun removeUseData() {}


}
