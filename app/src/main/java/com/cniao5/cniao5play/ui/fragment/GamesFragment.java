package com.cniao5.cniao5play.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerAppInfoComponent;
import com.cniao5.cniao5play.di.module.AppInfoModule;
import com.cniao5.cniao5play.presenter.AppInfoPresenter;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;

/**
 * Created by Ivan on 16/9/26.
 */

public class GamesFragment extends BaseAppInfoFragment {




//    @Override
//    public void setupAcitivtyComponent(AppComponent appComponent) {

//        DaggerTopListComponent.builder().build().inject();

//        DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this))
//                .build().injectGamesFragment(this);
//    }

    @Override
    int type() {
        return AppInfoPresenter.GAME;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return  AppInfoAdapter.builder().showPosition(false).showBrief(true).showCategoryName(true).build();
    }
}
