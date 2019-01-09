package com.cniao5.cniao5play.di.module;

import android.app.Application;

import com.cniao5.cniao5play.common.http.CommonParamsInterceptor;
import com.cniao5.cniao5play.common.rx.RxErrorHandler;
import com.cniao5.cniao5play.data.http.ApiService;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di.module
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@Module
public class HttpModule {





    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application, Gson gson){



        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//        当打开时每次下载都会记录日志,导致内存溢出，OOM

        // Throwing OutOfMemoryError "Failed to allocate a 68 byte allocation with 2112 free
        // bytes and 2112B until OOM" (recursive case)

//        if(BuildConfig.DEBUG){
//            // log用拦截器
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//
//            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            builder.addInterceptor(logging);
//
//        }





        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
//        SSLSocketFactory sslSocketFactory = null;

        return builder
                .addInterceptor(new CommonParamsInterceptor(application,gson))

                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)

                .build();


    }



    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);


        return builder.build();

    }



    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){

        return  retrofit.create(ApiService.class);
    }


    @Provides
    @Singleton
    public RxErrorHandler provideErrorHandlder(Application application){

        return  new RxErrorHandler(application);
    }



}
