package com.lsky.ener.util

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.leifu.mvpkotlin.R
import com.leifu.mvpkotlin.app.MyApp


/**
 * 创建人:雷富
 * *创建时间:2018/7/30 17:38
 * * 描述: 2016/8/4.
 */
@SuppressLint("StaticFieldLeak")
object ToastUtil {
    private var mToast: Toast? = null
    private var mExitTime: Long = 0
    private var toast: Toast? = null
    private var contentView: View? = null


    fun shortShow(msg: String) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        create(msg).show()
    }


    private fun create(msg: String): Toast {
        if (toast == null) {
            toast = Toast(MyApp.context)
        }
        if (contentView == null) {
            contentView = View.inflate(MyApp.context, R.layout.dialog_toast, null)
        }
        val tvMsg = contentView!!.findViewById<View>(R.id.tv_toast_msg) as TextView
        tvMsg.text = msg
        toast!!.view = contentView
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        toast!!.duration = Toast.LENGTH_SHORT
        return toast as Toast
    }


    /**
     * Toast 替代方法 ：立即显示无需等待
     */
    fun showToast(msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(msg)
        }
        mToast!!.show()
    }

    fun doubleClickExit(): Boolean {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            showToast("再按一次退出")
            mExitTime = System.currentTimeMillis()
            return false
        }
        return true
    }
}

