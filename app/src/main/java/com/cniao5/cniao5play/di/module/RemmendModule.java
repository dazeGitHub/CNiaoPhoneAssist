package com.cniao5.cniao5play.di.module;

import android.app.ProgressDialog;

import com.cniao5.cniao5play.data.AppInfoModel;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;
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




@Module
public class RemmendModule {



    private AppInfoContract.View mView;

    public RemmendModule(AppInfoContract.View view){


        this.mView = view;
    }





    @Provides
    public AppInfoContract.View provideView(){

        return mView;
    }



    @Provides
    public AppInfoModel privodeModel(ApiService apiService){

        return  new AppInfoModel(apiService);
    }



    @Provides
    public ProgressDialog provideProgressDialog(AppInfoContract.View view){

        return new ProgressDialog(((RecommendFragment)view).getActivity());
    }




}
