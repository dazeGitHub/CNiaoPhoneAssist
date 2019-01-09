package com.cniao5.cniao5play.data;

import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.data.http.Providers;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BaseModel {

    protected ApiService mApiService;
    protected Providers mProviders;

    public BaseModel(ApiService apiService, Providers providers) {
        this.mApiService = apiService;
        this.mProviders = providers;
    }

    public BaseModel(ApiService apiService){
        this.mApiService = apiService;
    }

    //???
    public void onDestory() {
        this.mApiService = null;
        this.mProviders = null;
    }


}
