package com.cniao5.cniao5play.presenter;

import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.bean.SubjectDetail;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.subscriber.ErrorHandlerSubscriber;
import com.cniao5.cniao5play.common.rx.subscriber.ProgressSubcriber;

import javax.inject.Inject;

import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/7/9.
 */

public class SubjectPresenter extends BasePresenter<SubjectContract.ISubjectModel, SubjectContract.SubjectView> {

    @Inject
    public SubjectPresenter(SubjectContract.ISubjectModel iSubjectModel,
                            SubjectContract.SubjectView subjectView) {
        super(iSubjectModel, subjectView);
    }

    public void getSubjects(int page) {

        Observer subscriber = null;

        if (page == 0) {

            subscriber = new ProgressSubcriber<PageBean<Subject>>(mContext, mView) {
                @Override
                public void onNext(PageBean<Subject> pageBean) {
                    mView.showSubjects(pageBean);
                }
            };
        } else {
            subscriber = new ErrorHandlerSubscriber<PageBean<Subject>>(mContext) {
                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean<Subject> pageBean) {

                    mView.showSubjects(pageBean);
                }
            };
        }

        mModel.getSubjects(page)
                .compose(RxHttpReponseCompat.<PageBean<Subject>>compatResult())
                .subscribe(subscriber);
    }

    public void getSubjectDetail(int id) {

        mModel.getSubjectDetail(id).compose(RxHttpReponseCompat.<SubjectDetail>compatResult())
                .subscribe(new ProgressSubcriber<SubjectDetail>(mContext, mView) {
                    @Override
                    public void onNext(SubjectDetail subjectDetail) {

                        mView.showSubjectDetail(subjectDetail);
                    }
                });
    }

}
