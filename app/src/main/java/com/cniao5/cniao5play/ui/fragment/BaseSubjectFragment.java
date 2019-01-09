<<<<<<< HEAD
package com.cniao5.cniao5play.ui.fragment;

import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.bean.SubjectDetail;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerSubjectComponent;
import com.cniao5.cniao5play.di.module.SubjectModule;
import com.cniao5.cniao5play.presenter.SubjectContract;
import com.cniao5.cniao5play.presenter.SubjectPresenter;

/**
 * Created by Administrator on 2017/7/9.
 */


public  abstract  class BaseSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView {


    @Override
    public void showSubjects(PageBean<Subject> subjects) {


    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {

    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

        DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this))
                .build().inject(this);
    }
}
=======
package com.cniao5.cniao5play.ui.fragment;

import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.bean.SubjectDetail;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerSubjectComponent;
import com.cniao5.cniao5play.di.module.SubjectModule;
import com.cniao5.cniao5play.presenter.SubjectContract;
import com.cniao5.cniao5play.presenter.SubjectPresenter;

/**
 * Created by Administrator on 2017/7/9.
 */


public  abstract  class BaseSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView {


    @Override
    public void showSubjects(PageBean<Subject> subjects) {


    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {

    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

        DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this))
                .build().inject(this);
    }
}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
