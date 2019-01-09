package com.cniao5.cniao5play.data;

import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.LoginBean;
import com.cniao5.cniao5play.bean.requestbean.LoginRequestBean;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.presenter.contract.LoginContract;

import io.reactivex.Observable;


/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.data
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class LoginModel implements LoginContract.ILoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService apiService){
        this.mApiService = apiService;

    }

    @Override
    public Observable<BaseBean<LoginBean>> login(String phone, String pwd) {

        LoginRequestBean param = new LoginRequestBean();
        param.setEmail(phone);
        param.setPassword(pwd);

        return mApiService.login(param);
    }
}
