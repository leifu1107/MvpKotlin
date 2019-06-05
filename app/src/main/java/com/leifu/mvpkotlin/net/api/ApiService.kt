package com.leifu.mvpkotlin.net.api

import com.leifu.mvpkotlin.net.BaseBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:16
 *描述:
 */
interface ApiService {
    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<BaseBean>
}