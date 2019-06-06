package com.leifu.mvpkotlin.net.api

import com.leifu.mvpkotlin.net.BaseBean
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:16
 *描述:
 */
interface ApiService {
    /**
     * 首页精选
     */
    @GET("api/4/news/latest")
    fun getFirstHomeData(): Flowable<BaseBean>
}