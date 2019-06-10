package com.leifu.mvpkotlin.net


import com.leifu.mvpkotlin.net.except.ApiException
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

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
                if (baseBean.code == 1) {
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
                if (baseBean.code == 1) {
                    createFlowable(bean)
                } else {
                    Flowable.error(ApiException(baseBean.msg))
                }
            })
        }
    }

    /**
     * 生成Flowable,支持背压
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
