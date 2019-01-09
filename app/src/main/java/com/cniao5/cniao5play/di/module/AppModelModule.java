package com.cniao5.cniao5play.di.module;

import com.cniao5.cniao5play.data.AppInfoModel;
import com.cniao5.cniao5play.data.http.ApiService;

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


//@Module(includes = {CacheModule.class})
@Module
public class AppModelModule {

//    @Provides
//    public AppInfoModel privodeModel(ApiService apiService, Providers providers) {
//
//        return new AppInfoModel(apiService, providers);
//    }

    @Provides
    public AppInfoModel privodeModel(ApiService apiService) {

        return new AppInfoModel(apiService);
    }

}
