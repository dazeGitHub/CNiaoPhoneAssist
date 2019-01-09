package com.cniao5.cniao5play.common.rx;

import android.util.Log;

import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.common.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.rx
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class RxHttpReponseCompat {


    public static <T> ObservableTransformer<BaseBean<T>, T> compatResult() {

        //将 Observable<BaseBean<T>> 转换为 ObservableSource<T> 类型
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> baseBeanObservable) {
                return baseBeanObservable.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseBean<T> tBaseBean) throws Exception {
                        if (tBaseBean.success()) {
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {

                                    try {
                                        subscriber.onNext(tBaseBean.getData());
                                        subscriber.onComplete();
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        } else {
                            Log.e("TAG", "RxHttpReponseCompat ObservableTransformer" + tBaseBean.getMessage());
                            return Observable.error(new ApiException(tBaseBean.getStatus(), tBaseBean.getMessage()));
                        }

                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };

    }

    public <T> ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//
//
//
//    public static  <T> Observable.Transformer<BaseBean<T>,T> compatResult(){
//
//
//
//
//        return  new Observable.Transformer<BaseBean<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<BaseBean<T>> baseBeanObservable) {
//
//
//
//                return baseBeanObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(final BaseBean<T> tBaseBean) {
//
//                        if(tBaseBean.success()){
//
//
//                           return Observable.create(new Observable.OnSubscribe<T>() {
//                                @Override
//                                public void call(Subscriber<? super T> subscriber) {
//
//                                    try {
//                                        subscriber.onNext(tBaseBean.getData());
//                                        subscriber.onCompleted();
//                                    }
//                                    catch (Exception e){
//                                        subscriber.onError(e);
//                                    }
//
//
//                                }
//                            });
//
//
//                        }
//                        else {
//                            return  Observable.error(new ApiException(tBaseBean.getStatus(),tBaseBean.getMessage()));
//                        }
//
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
//            }
//        };
//
//
//    }
}
