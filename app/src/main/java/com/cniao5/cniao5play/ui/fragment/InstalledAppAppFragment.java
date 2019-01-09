package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cniao5.cniao5play.bean.AppInfoBean;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.common.rx.RxBus;
import com.cniao5.cniao5play.ui.adapter.AndroidApkAdapter;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/8.
 */

public class InstalledAppAppFragment extends AppManangerFragment {

    private AndroidApkAdapter mAdapter;

    @Override
    public void init() {
        super.init();
        mPresenter.getInstalledApps();


        RxBus.getDefault().toObservable(AppInfoBean.class).subscribe(new Consumer<AppInfoBean>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull AppInfoBean appInfoBean) throws Exception {

                Log.i("InstalledAppAppFragment", "accept uninstall receive" + appInfoBean.getPackageName());
                if (AppInfoBean.APP_UNINSTALL == appInfoBean.getAppState()) { //如果卸载成功
                    List<AndroidApk> list = mAdapter.getData();
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        AndroidApk androidApk = list.get(i);
                        if (appInfoBean.getPackageName().equals(androidApk.getPackageName())) {
                            mAdapter.remove(i);
                            break;
                        }
                    }
                }
            }
        });

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APP);
        return mAdapter;
    }


    @Override
    public void showApps(List<AndroidApk> apps) {
        mAdapter.addData(apps);
    }
}
