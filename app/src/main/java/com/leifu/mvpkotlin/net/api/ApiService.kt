package com.leifu.mvpkotlin.net.api

import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.net.BaseBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:16
 *描述:
 */
interface ApiService {
    // https://www.apiopen.top/femaleNameApi?page=1
    //https://www.apiopen.top/createUserKey?appId=com.chat.peakchao&passwd=123456
    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getObjectData(): Observable<ObjectBean>

    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getListData(): Observable<BaseBean>
}