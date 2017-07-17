package com.cniao5.cniao5play.ui.widget;

import android.content.Context;
import android.util.Log;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppDownloadInfo;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.RxSchedulers;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.common.util.AppUtils;
import com.cniao5.cniao5play.common.util.PackageUtils;
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

/**
 * 该类只负责DownloadProgressButton的状态和点击
 */
public class DownloadButtonConntroller {


    private RxDownload mRxDownload;

//    private String mDownloadDir; // 文件下载的目录


    private Api mApi;


    public DownloadButtonConntroller(RxDownload downloader) {

        this.mRxDownload = downloader;

        if (mRxDownload != null) {


            mApi = mRxDownload.getRetrofit().create(Api.class);
        }

    }

    public void handDownloadBtnByDownRecord(final DownloadProgressButton btn, DownloadRecord record) {

        AppInfo info = downloadRecord2AppInfo(record);
        receiveDownloadStatus(record.getUrl()).subscribe(new DownloadConsumer(btn, info));
    }

    public void handDownloadBtnUpdate(DownloadProgressButton btn, final AppInfo appInfo) {

        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.setFlag(DownloadFlag.FILE_UPGRADE);
        Observable.just(downloadEvent).subscribe(new DownloadConsumer(btn, appInfo));
    }

    /**
     * 先根据AppInfo中的id获取AppDownloadInfo,然后根据AppDownloadInfo中的downloadUrl使用Rxdownload下载
     * 在RecyclerView 绑定数据时调用该方法显示下载状态
     *
     * @param btn
     * @param appInfo
     */
    public void handDownloadBtnByAppInfo(final DownloadProgressButton btn,
                                         final AppInfo appInfo) {

        if (mApi == null) {
            return;
        }

        isAppInstalled(btn.getContext(), appInfo)

                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event)
                            throws Exception {

                        if (DownloadFlag.UN_INSTALL == event.getFlag()) {

                            return isApkFileExsit(btn.getContext(), appInfo);

                        }
                        return Observable.just(event);


                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        //Normal的时候文件不存在,所以不会调用下边的方法,所以DownloadAppInfo为空
                        if (DownloadFlag.FILE_EXIST == event.getFlag()) {

                            return getAppDownloadInfo(appInfo)
                                    .flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                        @Override
                                        public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                            getAppInfoSize(appInfo, appDownloadInfo, "file exist");

                                            appInfo.setAppDownloadInfo(appDownloadInfo);

                                            return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                                        }
                                    });

                        }


                        return Observable.just(event);
                    }
                })
                .compose(RxSchedulers.<DownloadEvent>io_main())

                .subscribe(new DownloadConsumer(btn, appInfo));


    }

    private void bindDownProgBtnClick(final DownloadProgressButton btn, final AppInfo appInfo) {

        RxView.clicks(btn).subscribe(new Consumer<Object>() {


            @Override
            public void accept(@NonNull Object o) throws Exception {


                int flag = (int) btn.getTag(R.id.tag_apk_flag);


                switch (flag) {

                    case DownloadFlag.INSTALLED:

                        runApp(btn.getContext(), appInfo.getPackageName());
                        break;

                    // 升级 还加上去

                    case DownloadFlag.STARTED:
                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());
                        break;


                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                        startDownload(btn, appInfo);

                        break;

                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);

                        break;
                    case DownloadFlag.FILE_UPGRADE:
                        updateApp();
                        break;

                }


            }
        });
    }

    private void updateApp() {
        
    }

    private void installApp(Context context, AppInfo appInfo) {

//        mRxDownload.getRealFiles()
        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR)
                + File.separator
                + appInfo.getReleaseKeyHash() + ".apk";
        Log.e("DownBtnController", "installApp,path=" + path);

//        AppUtils.installApk(context, path);
        PackageUtils.install(context, path);
    }

    /**
     * NORMAL和Pause的时候都会调用startDownload方法
     * NORMAL时ApppDownloadInfo为空,所以需要获取AppDownloadInfo
     *
     * @param btn
     * @param appInfo
     */
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

                                        getAppInfoSize(appInfo, appDownloadInfo, "downloadInfoNull");
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

    private void getAppInfoSize(AppInfo appInfo, AppDownloadInfo appDownloadInfo, String pre) {
        String displayName = appInfo.getDisplayName();
        Log.e("DownBtnController",
                pre +
                        " displayName=" + displayName +
                        " appInfo.getApkSize=" + appInfo.getApkSize() +
                        " appDownloadInfo.getApkSize=" + appDownloadInfo.getApkSize() +
                        " releaseHashKey=" + appDownloadInfo.getReleaseKeyHash()
        );
    }

    private void download(DownloadProgressButton btn, AppInfo info) {

        //设置RxDownload后台下载      //在下载的时候将app信息保存到DownloadBean中
        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();

        //接收RxDownload下载状态
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


    private void pausedDownload(String url) {
        //这里必须订阅,否则不生效
        mRxDownload.pauseServiceDownload(url).subscribe();
    }

    private void runApp(Context context, String packageName) {

        AppUtils.runApp(context, packageName);
    }


    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {


        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> isApkFileExsit(Context context, AppInfo appInfo) {


        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(path);


        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return Observable.just(event);


    }


    public Observable<DownloadEvent> receiveDownloadStatus(String url) {

        return mRxDownload.receiveDownloadStatus(url);
    }


    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {

        return mApi.getAppDownloadInfo(appInfo.getId()).compose(RxHttpReponseCompat.<AppDownloadInfo>compatResult());
    }


    /**
     * 关于RecyclerView复用的问题：
     * 如选中第一个app进行下载,则之后复用该ViewHolder的条目都有相同的下载进度
     * 虽然每次appInfo不同,在handDownloadBtn中复用后传入的DownloadProgressButton是复用后的
     * 所以导致每次进度更新都在复用后的Button上
     * <p>
     * 解决方案：
     */
    class DownloadConsumer implements Consumer<DownloadEvent> {

        DownloadProgressButton mDownProgBtn;

        AppInfo mAppInfo;

        public DownloadConsumer(DownloadProgressButton b, AppInfo appInfo) {

            this.mDownProgBtn = b;
            this.mAppInfo = appInfo;
        }


        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

        }
    }


    interface Api {

        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownloadInfo(@Path("id") int id);
    }
}
