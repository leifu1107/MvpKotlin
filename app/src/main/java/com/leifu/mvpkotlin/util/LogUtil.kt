package com.lsky.ener.util

import android.text.TextUtils
import android.util.Log

/**
 * 创建人: 雷富
 * 创建时间: 2018/1/29 16:00
 * 描述:打印日志类,可打印超过2048长度的日志
 */
object LogUtil {
    private val isShowLog = true
    internal var p = 2048
    /**
     *  自定义Tag的前缀，可以是作者名
     */
    var customTagPrefix = "leilifu"

    private val callerStackTraceElement: StackTraceElement
        get() = Thread.currentThread().stackTrace[4]

    private fun generateTag(caller: StackTraceElement): String {
        var tag = "%s.%s(Line:%d)" // 占位符
        var callerClazzName = caller.className // 获取到类名
        callerClazzName = callerClazzName.substring(
            callerClazzName
                .lastIndexOf(".") + 1
        )
        tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber) // 替换
        tag = if (TextUtils.isEmpty(customTagPrefix))
            tag
        else
            customTagPrefix + ":" + tag
        return tag
    }

    fun e(tag: String, msg: String) {
        var msg = msg
        if (isShowLog) {
            val length = msg.length.toLong()
            if (length < p || length == p.toLong())
                Log.e(tag, msg)
            else {
                while (msg.length > p) {
                    val logContent = msg.substring(0, p)
                    msg = msg.replace(logContent, "")
                    Log.e(tag, logContent)
                }
                Log.e(tag, msg)
            }
        }
    }


    fun e(msg: String) {//默认提供类名方法名
        var msg = msg
        if (isShowLog) {
            val length = msg.length.toLong()
            if (length < p || length == p.toLong())
                Log.e(generateTag(callerStackTraceElement), msg)
            else {
                while (msg.length > p) {
                    val logContent = msg.substring(0, p)
                    msg = msg.replace(logContent, "")
                    Log.e(generateTag(callerStackTraceElement), logContent)
                }
                Log.e(generateTag(callerStackTraceElement), msg)
            }
        }
    }
}
