package com.cniao5.cniao5play.presenter.contract;

import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by Ivan on 2017/1/3.
 */

public interface AppManagerContract {




    interface AppManagerView extends BaseView{


        void showDownloading(List<DownloadRecord> downloadRecords);

        /**
         * 在DownloadedFragment和InstalledAppFrgment
         * @param apps
         */
        void showApps(List<AndroidApk> apps);

        void showUpdateApps(List<AppInfo> appInfos);

    }

    interface IAppManagerModel{

        Observable<List<DownloadRecord>> getDownloadRecord();

        RxDownload getRxDownload();

        Observable<List<AndroidApk>> getLocalApks();

        Observable<List<AndroidApk>> getInstalledApps();
    }

}
