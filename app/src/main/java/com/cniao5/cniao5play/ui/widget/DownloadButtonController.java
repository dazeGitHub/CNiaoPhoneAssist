package com.cniao5.cniao5play.ui.widget;

import android.content.Context;
import android.util.Log;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppDownloadInfo;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.RxSchedulers;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.common.util.AppUtils;
import com.cniao5.cniao5play.common.util.PermissionUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.widget
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class DownloadButtonController {

    private RxDownload mRxDownload;
    private Api mApi;

    public DownloadButtonController(RxDownload rxDownload) {
        mRxDownload = rxDownload;
        if (mRxDownload != null) {
            mApi = mRxDownload.getRetrofit().create(Api.class);
        }
    }

    //    public void handDownloadBtn(final DownloadProgressButton btn, final AppInfo appInfo) {
//        isAppInstalled(btn.getContext(), appInfo).subscribe(new Consumer<DownloadEvent>() {
//            @Override
//            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//
//                if (downloadEvent.getFlag() == DownloadFlag.INSTALLED) {
//
//                } else {
//
//                    isApkFileExsit(appInfo).subscribe(new Consumer<DownloadEvent>() {
//                        @Override
//                        public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//
//                            if (downloadEvent.getFlag() == DownloadFlag.NORMAL) {
//
//                            } else {
//
//                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
//                                    @Override
//                                    public void accept(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {
//
//                                        receiveDownloadStatus(appDownloadInfo).subscribe(new Consumer<DownloadEvent>() {
//                                            @Override
//                                            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//
//                                            }
//                                        });
//
//                                    }
//                                });
//
//                            }
//
//                        }
//                    });
//
//                }
//            }
//        });
//
//    }

    public void handDownloadBtnByDownRecord(final DownloadProgressButton btn, DownloadRecord record) {

        AppInfo info = downloadRecord2AppInfo(record);
        receiveDownloadStatus(record.getUrl()).subscribe(
                new DownloadConsumer(btn, info));
    }

    public void handDownloadBtnUpdate(DownloadProgressButton btn, final AppInfo appInfo) {

        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.setFlag(DownloadFlag.FILE_UPGRADE);
        Observable.just(downloadEvent).subscribe(new DownloadConsumer(btn, appInfo));
    }


    public void handDownloadBtnByAppInfo(final DownloadProgressButton btn, final AppInfo appInfo) {

        if (mApi == null) {
            return;
        }

        bindClick(btn, appInfo);

        isAppInstalled(btn.getContext(), appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent downloadEvent)
                            throws Exception {
                        if (DownloadFlag.UN_INSTALL == downloadEvent.getFlag()) {
                            return isApkFileExsit(btn.getContext(), appInfo);
                        }
                        //INSTALLED
                        return Observable.just(downloadEvent);
                    }
                }).flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {

            @Override
            public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent downloadEvent) throws Exception {
                //FILE_EXIST
                if (DownloadFlag.FILE_EXIST == downloadEvent.getFlag()) {
                    //包完整
                    if (AndroidApk.read(btn.getContext(), getApkFilePath(btn.getContext(), appInfo)) != null) {
                        downloadEvent.setFlag(DownloadFlag.COMPLETED);
                        return Observable.just(downloadEvent);
                    } else {
                        //包不完整则使用 Rxdownload 判断状态
                        return getAppDownloadInfo(appInfo).flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                            @Override
                            public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                appInfo.setAppDownloadInfo(appDownloadInfo);
                                return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                            }
                        });
                    }
                }
                //NORMAL,INSTALLED
                return Observable.just(downloadEvent);
            }
        })
                .compose(RxSchedulers.<DownloadEvent>io_main())
                .subscribe(new DownloadConsumer(btn, appInfo));

    }


    public AppInfo downloadRecord2AppInfo(DownloadRecord bean) {


        AppInfo info = new AppInfo();

        info.setId(Integer.parseInt(bean.getExtra1()));
        info.setIcon(bean.getExtra2());
        info.setDisplayName(bean.getExtra3());
        info.setPackageName(bean.getExtra4());
        info.setReleaseKeyHash(bean.getExtra5());


        AppDownloadInfo downloadInfo = new AppDownloadInfo();

        downloadInfo.setDowanloadUrl(bean.getUrl());

        info.setAppDownloadInfo(downloadInfo);

        return info;

    }

    class DownloadConsumer implements Consumer<DownloadEvent> {

        private DownloadProgressButton mDownloadProgressButton;
        AppInfo mAppInfo;

        public DownloadConsumer(DownloadProgressButton downloadProgressButton, AppInfo appInfo) {
            this.mDownloadProgressButton = downloadProgressButton;
            this.mAppInfo = appInfo;
        }

        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

            int flag = event.getFlag();

            //打Log.d()打不出来，我草尼玛的，我草死你妈
            Log.e("DownBtnController", "DownloadConsumer  accept,flag=" + flag);

            mDownloadProgressButton.setTag(R.id.tag_apk_flag, flag);

//            bindClick(mDownloadProgressButton,mAppInfo);

            switch (flag) {
                case DownloadFlag.INSTALLED:
                    mDownloadProgressButton.setText("运行");
                    break;
                case DownloadFlag.NORMAL:
                    //当RecyclerView的ViewHolder复用的时候,会走到NORMAL,但是mDownloadProgressButton的progress还是之前
                    //下载的Progress
                    mDownloadProgressButton.download();
                    break;
                case DownloadFlag.STARTED:
                    int percentNumber = (int) event.getDownloadStatus().getPercentNumber();
                    Log.e("DownBtnController", "startProgressNumber = " + percentNumber);
                    mDownloadProgressButton.setProgress(percentNumber);
                    break;
                case DownloadFlag.PAUSED:
                    int pausePercentNumber = (int) event.getDownloadStatus().getPercentNumber();
                    Log.e("DownBtnController", "pausePercentNumber = " + pausePercentNumber);
                    mDownloadProgressButton.setProgress(pausePercentNumber);
                    mDownloadProgressButton.paused();
                    break;
                case DownloadFlag.COMPLETED: //已完成
                    mDownloadProgressButton.setText("安装");
//                    installApp(btn.getContext(),mAppInfo);
                    break;
                case DownloadFlag.FAILED://下载失败
                    mDownloadProgressButton.setText("失败");
                    break;
                case DownloadFlag.DELETED: //已删除

                    break;
            }
        }

    }

    private void bindClick(final DownloadProgressButton btn, final AppInfo appInfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                switch (flag) {
                    case DownloadFlag.INSTALLED:
                        //已安装则点击运行
                        runApp(btn.getContext(), appInfo.getPackageName());
                        break;
                    case DownloadFlag.STARTED:
                        //已经开始下载则点击暂停,如果已经开始下载则appInfo.getAppDownloadInfo()肯定不为空
                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());
                        break;
                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                        startDownload(btn, appInfo);
                        break;
                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);
                        break;
                }
            }
        });
    }


    private void runApp(Context context, String packageName) {
        AppUtils.runApp(context, packageName);
    }

    private void pausedDownload(String url) {
        mRxDownload.pauseServiceDownload(url).subscribe();
    }

    private void startDownload(final DownloadProgressButton btn, final AppInfo appInfo) {

        PermissionUtil.requestPermisson(btn.getContext(), WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            final AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
                            if (downloadInfo == null) {
                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {
                                        appInfo.setAppDownloadInfo(appDownloadInfo);
                                        download(btn, appInfo);
                                    }
                                });
                            } else {
                                download(btn, appInfo);
                            }
                        }

                    }
                });
    }

    private void download(DownloadProgressButton btn, AppInfo info) {
        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();
        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl()).subscribe(new DownloadConsumer(btn, info));
    }

    private DownloadBean appInfo2DownloadBean(AppInfo info) {

        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setUrl(info.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(info.getReleaseKeyHash() + ".apk");

        downloadBean.setExtra1(info.getId() + "");
        downloadBean.setExtra2(info.getIcon());
        downloadBean.setExtra3(info.getDisplayName());
        downloadBean.setExtra4(info.getPackageName());
        downloadBean.setExtra5(info.getReleaseKeyHash());

        return downloadBean;
    }

    private void installApp(Context context, AppInfo appInfo) {
//        mRxDownload.getRealFiles()
        final String dir = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR);
        String path = dir + File.separator + appInfo.getReleaseKeyHash() + ".apk";
        Log.e("DownBtnController", "installApp,path=" + path);
        AppUtils.installApk(context, path);
    }

    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {
        DownloadEvent event = new DownloadEvent();
        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);
        return Observable.just(event);
    }

    public Observable<DownloadEvent> isApkFileExsit(Context context, AppInfo appInfo) {
        File file = new File(getApkFilePath(context, appInfo));
        DownloadEvent event = new DownloadEvent();
        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);
        return Observable.just(event);
    }

    private String getApkFilePath(Context context, AppInfo appInfo) {
        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + appInfo.getReleaseKeyHash() + ".apk";
        return path;
    }

    /**
     * 根据AppDownloadInfo的url获取该App下载状态
     *
     * @return
     */

    public Observable<DownloadEvent> receiveDownloadStatus(String url) {
        return mRxDownload.receiveDownloadStatus(url);
    }


    /**
     * 联网根据AppInfo获取下载url
     *
     * @param appInfo
     * @return
     */
    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {
        return mApi.getAppDownloadInfo(appInfo.getId()).compose(RxHttpReponseCompat.<AppDownloadInfo>compatResult());
    }


    interface Api {
        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownloadInfo(@Path("id") int id);
    }

}
