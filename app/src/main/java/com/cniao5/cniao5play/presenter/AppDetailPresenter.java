package com.cniao5.cniao5play.presenter;

import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.subscriber.ProgressSubcriber;
import com.cniao5.cniao5play.data.AppInfoModel;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;

import javax.inject.Inject;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.presenter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppDetailPresenter extends BasePresenter<AppInfoModel,AppInfoContract.AppDetailView> {

    @Inject
    public AppDetailPresenter(AppInfoModel appInfoModel, AppInfoContract.AppDetailView appDetailView) {
        super(appInfoModel, appDetailView);
    }




    public void getAppDetail(int id){


        mModel.getAppDetail(id).compose(RxHttpReponseCompat.<AppInfo>compatResult())
                .subscribe(new ProgressSubcriber<AppInfo>(mContext,mView) {
                    @Override
                    public void onNext(AppInfo appInfo) {

                        mView.showAppDetail(appInfo);
                    }
                });
    }



}
