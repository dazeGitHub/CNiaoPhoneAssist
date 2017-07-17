package com.cniao5.cniao5play.ui.fragment;

import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerAppInfoComponent;
import com.cniao5.cniao5play.di.module.AppInfoModule;
import com.cniao5.cniao5play.presenter.AppInfoPresenter;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;


/**
 * Created by Ivan on 16/9/26.
 */

public class TopListFragment extends BaseAppInfoFragment {


    @Override
    int type() {
        return AppInfoPresenter.TOP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return  AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).rxDownload(mRxDownload).build();
    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

        DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this))
                .build().injectTopListFragment(this);

    }


}
