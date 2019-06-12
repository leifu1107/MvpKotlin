# MvpKotlin
:pig:  kotlin mvp框架的封装,   1.Rxjava2采用了背压和非背压,    2.实体类采用了泛型和继承两种    3.比大部分作者的框架封装和使用都简单高效
# 本项目采用 Kotlin 语言编写，结合 MVP+RxJava2+Retrofit2等的架构设计,封装比较简单,代码量少
----

![](https://github.com/leifu1107/MvpKotlin/raw/master/art/1.jpg) 
![](https://github.com/leifu1107/MvpKotlin/raw/master/art/2.jpg) 
```java
//可根据实际情况增加一些方法
interface IBaseView {

    /**
     * 加载中
     */
    fun showLoading()

    /**
     * 取消加载中
     */
    fun dismissLoading()

    /**
     * 显示错误提示
     */
    fun showErrorMsg(msg: String)
}
```
```java
interface IBasePresenter<in V : IBaseView> {

    fun attachView(view: V)

    fun detachView()
}
```
```java
open class BaseRxPresenter<V : IBaseView> : IBasePresenter<V> {
    var mView: V? = null
        private set

    private var compositeDisposable: CompositeDisposable? = null
    fun addSubscription(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }

    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        mView = null

        if (!compositeDisposable!!.isDisposed) {
            compositeDisposable!!.clear()
        }
    }
}
```

```java

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseMvpActivity<in V : IBaseView, P : IBasePresenter<V>> : BaseActivity(), IBaseView {

    var mPresenter: P? = null


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    /**
     * 加载中
     */
    override fun showLoading() {
        LoadingUtil.showLoading(mActivity, "加载中...")
    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
        LoadingUtil.dismissLoading()
    }

    /**
     * 显示错误提示
     */
    override fun showErrorMsg(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 请求网络数据
     */
    abstract fun startNetwork()

    /**
     * 创建Presenter
     */
    abstract fun createPresenter(): P
}
```


```java

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:24
 *描述:
 */
object RetrofitManager {
    val apiService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)  //自己配置
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message: String? -> Log.e("okhttp", message) })
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(MyApp.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
//            .addInterceptor(addQueryParameterInterceptor())  //参数添加
//            .addInterceptor(addHeaderInterceptor()) // token过滤
            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
//            .addNetworkInterceptor(addCacheInterceptor())// 添加缓存
//            .addInterceptor(addCacheInterceptor())// 添加缓存
            .cache(cache)  //添加缓存
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", "")
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
//                .header("token", token)
                .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isConnected()) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isConnected()) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }
}

```


```java

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述: 2016/8/3.
 * 统一线程处理
 *虽然在Rxjava2中，Flowable是在Observable的基础上优化后的产物，Observable能解决的问题Flowable也都能解决，但是并不代表Flowable可以完全取代Observable,在使用的过程中，并不能抛弃Observable而只用Flowable。
 *由于基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable慢得多。
 *只有在需要处理背压问题时，才需要使用Flowable。
 */
object RxManage {
    /**
     * compose简化线程切换
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerObservableHelper(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * compose简化线程切换
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerFlowableHelper(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleObservableResult(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.flatMap(Function<T, Observable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.code == 200) {//{"code":200,"msg":"成功!","data":{"appId":"com.chat.peakchao","appkey":"00d91e8e0cca2b76f515926a36db68f5"}}
                    createObservable(bean)
                } else {
                    Observable.error(ApiException(baseBean.msg))
                }
            })
        }
    }

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleFlowableResult(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.flatMap(Function<T, Flowable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.code == 200) {
                    createFlowable(bean)
                } else {
                    Flowable.error(ApiException(baseBean.msg))
                }
            })
        }
    }

    /**
     *不通过继承,通过泛型实现实体类
     * @param <T>
     * @return
    </T> */
    fun <T> handleFlowableResult2(): FlowableTransformer<BaseResponseBean<T>, T> {
        return FlowableTransformer { flowable ->
            flowable.flatMap { bean ->
                if (bean.code == 200) {
                    createFlowable(bean.data)
                } else {
                    Flowable.error(ApiException(bean.msg))
                }
            }
        }
    }

    /**
     * 生成Observable,不支持背压
     * @param <T>
     * @return
    </T> */
    private fun <T> createObservable(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    /**
     * 生成Flowable,支持背压
     * @param <T>
     * @return
    </T> */
    private fun <T> createFlowable(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }
}

```

```java
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

        fun handleException(e: Throwable, mView: IBaseView): String {
            e.printStackTrace()
            if (e is ApiException) {
                errorMsg = e.message.toString()
            } else if (e is HttpException) {
                if (e.code() == 403) {//可以处理一些退出登录逻辑,可以自己修改
                    mView.showErrorMsg("请重新登录")
                }
            } else if (e is SocketTimeoutException) {//网络超时
                errorMsg = "网络连接异常"
            } else if (e is ConnectException) { //均视为网络错误
                errorMsg = "网络连接异常"
            } else if (e is JsonParseException || e is JSONException || e is ParseException) {   //均视为解析错误
                Log.e("TAG", "数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
            } else if (e is UnknownHostException) {
                errorMsg = "网络连接异常"
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
            } else {//未知错误
                Log.e("TAG", "未知错误Debug调试: " + e.message)
                errorMsg = "未知错误，可能抛锚了吧~"
            }
            Log.e("TAG", "错误调试: " + e.message)
//            Toast.makeText(MyApp.context, errorMsg, Toast.LENGTH_LONG).show()
            mView.showErrorMsg(errorMsg)
            return errorMsg
        }

    }


}

```

```java
package com.leifu.mvpkotlin.presenter

import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.net.except.ExceptionHandle
import com.leifu.mvpkotlin.net.rx.RetrofitManager
import com.leifu.mvpkotlin.net.rx.RxManage
import com.leifu.mvpkotlin.presenter.contract.ObservableContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class ObservablePresenter : BaseRxPresenter<ObservableContract.View>(), ObservableContract.Presenter {


    override fun getObjectData() {
        mView?.showLoading()
        addSubscription(
            RetrofitManager.apiService.getObservableObjectData()
                .compose(RxManage.rxSchedulerObservableHelper())
                .compose(RxManage.handleObservableResult<ObjectBean>())
                .subscribe({
                    run { ->
                        mView?.showObjectData(it)
                        mView?.dismissLoading()
                    }
                },
                    { t ->
                        mView?.dismissLoading()
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    })
        )
    }
}
```
