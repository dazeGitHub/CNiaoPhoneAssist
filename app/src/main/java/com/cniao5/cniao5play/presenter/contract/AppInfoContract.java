package com.cniao5.cniao5play.presenter.contract;

import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.IndexBean;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.presenter.BasePresenter;
import com.cniao5.cniao5play.ui.BaseView;

import java.util.List;

/**
 * Created by Ivan on 2017/1/3.
 */

public interface AppInfoContract {


    interface View extends BaseView{





        void  showResult(IndexBean indexBean);


        void onRequestPermissonSuccess();
        void onRequestPermissonError();



    }


    interface AppInfoView extends BaseView{

        void  showResult(PageBean<AppInfo> page);

        void onLoadMoreComplete();



    }



    interface  AppDetailView extends BaseView{

        void showAppDetail(AppInfo appInfo);
    }


}
