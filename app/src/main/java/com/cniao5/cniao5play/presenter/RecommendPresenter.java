package com.cniao5.cniao5play.presenter;

import com.cniao5.cniao5play.bean.IndexBean;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.subscriber.ProgressSubcriber;
import com.cniao5.cniao5play.data.AppInfoModel;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;

import javax.inject.Inject;

/**
 * Created by Ivan on 2017/1/3.
 */

public class RecommendPresenter extends BasePresenter<AppInfoModel,AppInfoContract.View> {




    @Inject
    public RecommendPresenter(AppInfoModel model, AppInfoContract.View view) {
        super(model, view);

    }







    public void requestDatas() {


            mModel.index().compose(RxHttpReponseCompat.<IndexBean>compatResult())
                    .subscribe(new ProgressSubcriber<IndexBean>(mContext,mView) {
                        @Override
                        public void onNext(IndexBean indexBean) {

                            mView.showResult(indexBean);
                        }
                    });


    }
}
