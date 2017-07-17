package com.cniao5.cniao5play.data;



import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.requestbean.AppsUpdateBean;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.presenter.contract.MainContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.data.model
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class MainModel implements MainContract.IMainModel {

    private ApiService mApiService;

    public MainModel(ApiService apiService){
        this.mApiService = apiService;
    }


    @Override
    public Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean param) {
        return mApiService.getAppsUpdateinfo(param.getPackageName(),param.getVersionCode());
    }
}
