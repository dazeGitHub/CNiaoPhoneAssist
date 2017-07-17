package com.cniao5.cniao5play.data;

import android.content.Context;
import android.util.Log;

import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.common.exception.NoDataException;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.common.util.AppUtils;
import com.cniao5.cniao5play.common.util.PermissionUtil;
import com.cniao5.cniao5play.presenter.contract.AppManagerContract;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.data
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppManagerModel implements AppManagerContract.IAppManagerModel {


    private RxDownload mRxDownload;
    private Context mContext;

    public AppManagerModel(Context context, RxDownload rxDownload) {

        this.mContext = context;

        mRxDownload = rxDownload;
    }

    @Override
    public Observable<List<DownloadRecord>> getDownloadRecord() {
        return mRxDownload.getTotalDownloadRecords();
    }

    @Override
    public Observable<List<AndroidApk>> getInstalledApps() {

        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {
                e.onNext(AppUtils.getInstalledApps(mContext));

                e.onComplete();
            }
        });
    }

    @Override
    public RxDownload getRxDownload() {
        return mRxDownload;
    }

    @Override
    public Observable<List<AndroidApk>> getLocalApks() {

        final String dir = ACache.get(mContext).getAsString(Constant.APK_DOWNLOAD_DIR);

        return PermissionUtil.requestPermisson(mContext, WRITE_EXTERNAL_STORAGE).flatMap(new Function<Boolean, ObservableSource<List<AndroidApk>>>() {
            @Override
            public ObservableSource<List<AndroidApk>> apply(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
                        @Override
                        public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {

                            e.onNext(scanApks(dir));
                            e.onComplete();
                        }
                    });
                } else {
                    Log.e("AppManagerModel", "权限不够");
                    return Observable.empty();
                }

            }
        });

    }


    private List<AndroidApk> scanApks(String dir) throws NoDataException {


        File file = new File(dir);


        if (!file.isDirectory()) {

            throw new RuntimeException("is not Dir");
        }


        File[] apks = file.listFiles(new FileFilter() {


            @Override
            public boolean accept(File f) {


                if (f.isDirectory()) {
                    return false;
                }

                return f.getName().endsWith(".apk");
            }
        });


        List<AndroidApk> androidApks = new ArrayList<>();


        if (apks != null) {

            for (File apk : apks) {
                AndroidApk androidApk = AndroidApk.read(mContext, apk.getPath());
                if (androidApk != null) {
                    androidApks.add(androidApk);
                }
            }

        } else {
            throw new NoDataException(NoDataException.NO_DATA, "暂无数据");
        }
        return androidApks;

    }

}
