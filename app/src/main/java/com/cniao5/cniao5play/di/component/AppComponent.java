package com.cniao5.cniao5play.di.component;

import android.app.Application;

import com.cniao5.cniao5play.common.DownloadModule;
import com.cniao5.cniao5play.common.rx.RxErrorHandler;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.di.module.AppModule;
import com.cniao5.cniao5play.di.module.HttpModule;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import zlc.season.rxdownload2.RxDownload;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di.component
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */


@Singleton
@Component(modules = {AppModule.class, HttpModule.class,DownloadModule.class})
public interface AppComponent {


    public ApiService getApiService();

    public Application getApplication();

    public RxErrorHandler getRxErrorHandler();


//    public

    public RxDownload getRxDownload();

    public Gson getGson();
}
