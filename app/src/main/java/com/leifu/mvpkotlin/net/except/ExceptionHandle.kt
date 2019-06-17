package com.leifu.mvpkotlin.net.except

import android.util.Log
import com.google.gson.JsonParseException
import com.leifu.mvpkotlin.base.IBaseView
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * desc: 异常处理类
 */

class ExceptionHandle {
    companion object {
        var errorMsg = "请求失败，请稍后重试"

        fun handleException(e: Throwable, mView: IBaseView?): String {
            e.printStackTrace()
            if (e is ApiException) {
                errorMsg = e.message.toString()
            } else if (e is HttpException) {
                if (e.code() == 403) {//可以处理一些退出登录逻辑,可以自己修改
                    mView?.showErrorMsg("请重新登录")
                }
            } else if (e is SocketTimeoutException || e is ConnectException || e is UnknownHostException) { //均视为网络错误
                errorMsg = "网络连接异常"
                mView?.showNoNetwork()
            } else if (e is JsonParseException || e is JSONException || e is ParseException) {   //均视为解析错误
                Log.e("TAG", "数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
                mView?.showError()
            } else {//未知错误
                Log.e("TAG", "未知错误Debug调试: " + e.message)
                errorMsg = "未知错误，可能抛锚了吧~"
            }
            Log.e("TAG", "错误调试: " + e.message)
//            Toast.makeText(MyApp.context, errorMsg, Toast.LENGTH_LONG).show()
            mView?.showErrorMsg(errorMsg)
            return errorMsg
        }

    }


}
