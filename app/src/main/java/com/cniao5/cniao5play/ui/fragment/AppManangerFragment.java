package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerAppManagerComponent;
import com.cniao5.cniao5play.di.module.AppManagerModule;
import com.cniao5.cniao5play.presenter.AppManagerPresenter;
import com.cniao5.cniao5play.presenter.contract.AppManagerContract;
import com.cniao5.cniao5play.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.fragment
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public abstract class AppManangerFragment extends ProgressFragment<AppManagerPresenter>  implements AppManagerContract.AppManagerView {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    @Override
    public void init() {
        setupRecyclerView();
    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this))
                .build().inject(this);

    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    private void setupRecyclerView() {


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);


        mRecyclerView.setAdapter(setupAdapter());
    }

    @Override
    public void showApps(List<AndroidApk> apps) {

    }

    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {

    }

    @Override
    public void showUpdateApps(List<AppInfo> appInfos) {

    }

    protected abstract RecyclerView.Adapter setupAdapter();

}
