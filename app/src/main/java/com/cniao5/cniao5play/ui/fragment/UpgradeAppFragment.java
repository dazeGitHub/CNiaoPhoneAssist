package com.cniao5.cniao5play.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpgradeAppFragment extends AppManangerFragment {


    AppInfoAdapter mAdapter;


    public UpgradeAppFragment() {
        // Required empty public constructor
    }


    @Override
    public void init() {
        super.init();
        mPresenter.getUpdateApps();
    }


    @Override
    protected RecyclerView.Adapter setupAdapter() {

        mAdapter = AppInfoAdapter.builder().updateStatus(true).rxDownload(mPresenter.geRxDowanload()).build();

        return mAdapter;
    }


    @Override
    public void showUpdateApps(List<AppInfo> appInfos) {
        mAdapter.addData(appInfos);
    }
}
