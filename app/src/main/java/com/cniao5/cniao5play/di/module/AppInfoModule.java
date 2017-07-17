package com.cniao5.cniao5play.di.module;

import android.app.ProgressDialog;

import com.cniao5.cniao5play.data.AppInfoModel;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;
import com.cniao5.cniao5play.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */




@Module(includes = {AppModelModule.class})
public class AppInfoModule {



    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView view){


        this.mView = view;
    }





    @Provides
    public AppInfoContract.AppInfoView provideView(){

        return mView;
    }






}
