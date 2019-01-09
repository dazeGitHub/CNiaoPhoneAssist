package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.ui.adapter.AndroidApkAdapter;

import java.util.List;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.fragment
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

/**
 * 如果已经下载，则 显示等待安装，按钮显示安装，点击按钮进行安装
 * 如果已经安装，则 显示已安装，按钮显示删除，点击条目进行运行
 */
public class DownloadedFragment extends AppManangerFragment {


    private AndroidApkAdapter mAndroidApkAdapter;

    @Override
    public void init() {
        super.init();

        mPresenter.getLocalApks();
    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAndroidApkAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APK);
        return mAndroidApkAdapter;
    }


    /**
     * 在showApps(){}中showEmptyView()什么的都不行，因为这是在onNext()时候调用的,
     * 最后还有个onComplete()将contentView显示出来,坑
     * @param apps
     */
    @Override
    public void showApps(List<AndroidApk> apps) {
        mAndroidApkAdapter.addData(apps);
    }
}
