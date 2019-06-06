package com.leifu.mvpkotlin.net


import com.leifu.mvpkotlin.net.except.ApiException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述: 2016/8/3.
 */
object RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> handleResult(): FlowableTransformer<T, T> {   //compose判断结果
        return FlowableTransformer { httpResponseFlowable ->
            httpResponseFlowable.flatMap(Function<T, Flowable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.code == 1) {
                    createFlowable(bean)
                } else {
                    Flowable.error(ApiException(baseBean.msg))
                }
            })
        }
    }


    /**
     * 生成Flowable
     *
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
