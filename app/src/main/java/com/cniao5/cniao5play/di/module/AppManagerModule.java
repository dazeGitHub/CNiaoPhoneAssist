package com.cniao5.cniao5play.di.module;

import android.app.Application;

import com.cniao5.cniao5play.data.AppManagerModel;
import com.cniao5.cniao5play.presenter.contract.AppManagerContract;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */




@Module()
public class AppManagerModule {



    private AppManagerContract.AppManagerView mView;

    public AppManagerModule(AppManagerContract.AppManagerView  view){


        this.mView = view;
    }




    @Provides
    public AppManagerContract.AppManagerView  provideView(){

        return mView;
    }



    @Provides
    public AppManagerContract.IAppManagerModel provideModel(Application application, RxDownload rxDownload){

        return  new AppManagerModel(application,rxDownload);
    }










}
