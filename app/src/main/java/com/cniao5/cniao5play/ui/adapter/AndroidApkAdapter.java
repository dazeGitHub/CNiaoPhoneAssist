package com.cniao5.cniao5play.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.common.apkparset.AndroidApk;
import com.cniao5.cniao5play.common.rx.RxSchedulers;
import com.cniao5.cniao5play.common.util.AppUtils;
import com.cniao5.cniao5play.common.util.FileUtils;
import com.cniao5.cniao5play.common.util.PackageUtils;
import com.cniao5.cniao5play.ui.widget.DownloadProgressButton;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/7.
 */

/**
 * 已下载完成的[已安装/未安装]，不需要RxDownload
 * 如果包不完整则直接发生解析异常,过滤掉
 */
public class AndroidApkAdapter extends BaseQuickAdapter<AndroidApk, BaseViewHolder> {

    public static final int FLAG_APK = 0;
    public static final int FLAG_APP = 1;

    private int flag;

    public AndroidApkAdapter(int flag) {
        super(R.layout.template_apk);
        this.flag = flag;
    }


    @Override
    protected void convert(BaseViewHolder helper, final AndroidApk item) {

        helper.setText(R.id.txt_app_name, item.getAppName());
        helper.setImageDrawable(R.id.img_app_icon, item.getDrawable());

        //?
        helper.addOnClickListener(R.id.btn_action);


        final DownloadProgressButton btn = helper.getView(R.id.btn_action);
        final TextView txtStatus = helper.getView(R.id.txt_status);

        if (flag == FLAG_APK) {
            btn.setTag(R.id.tag_package_name, item.getPackageName());
            btn.setText("删除");

            RxView.clicks(btn).subscribe(new Consumer<Object>() {
                @Override
                public void accept(@NonNull Object o) throws Exception {

                    Log.e("AndroidApkAdapter", "RxView.clicks ,item.getApkPath()=" + item.getApkPath());

                    if (btn.getTag(R.id.tag_package_name).toString().equals(item.getPackageName())) {

                        Object obj = btn.getTag(); //是否安装

                        if (obj == null) {

                            //调用这个直接打开辅助功能了，，
                            PackageUtils.install(mContext, item.getApkPath());

//                          AppUtils.installApk(mContext, item.getApkPath());

                        } else {
                            boolean isInstall = (boolean) obj;

                            if (isInstall) {
                                deleteApk(item, (String) btn.getTag(R.id.tag_package_name));
                            } else {

                                PackageUtils.install(mContext, item.getApkPath());

//                              AppUtils.installApk(mContext, item.getApkPath());
                            }

                        }
                    } else {
                        Log.e("AndroidApkAdapter", "RxView.clicks  NotEquals");
                    }

                }
            });

            isInstalled(mContext, item.getPackageName()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {

                    btn.setTag(aBoolean);


                    if (aBoolean) {
                        txtStatus.setText("已安装");
                        btn.setText("删除");
                    } else {
                        txtStatus.setText("等待安装");
                        btn.setText("安装");
                    }
                }
            });
        } else if (flag == FLAG_APP) {

            btn.setText("卸载");
            RxView.clicks(btn).subscribe(new Consumer<Object>() {

                @Override
                public void accept(@NonNull Object o) throws Exception {
                    //点击后卸载,卸载完成刷新列表
                    AppUtils.uninstallApk(mContext, item.getPackageName());
                }
            });

            txtStatus.setText("v" + item.getAppVersionName() + " " + (item.isSystem() ? "内置" : "第三方") + "  " +
                    AppUtils.getAppSize(mContext, item.getPackageName()) / 1000 / 1000 + "MB");

        }

    }


    //没有position,尼玛
    private void deleteApk(AndroidApk item, String packageName) {

        // 1. 删除下载记录
        // 2. 删除文件
        List<AndroidApk> list = this.getData();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AndroidApk androidApk = list.get(i);
            if (packageName.equals(androidApk.getPackageName())) {
                this.remove(i);
                break;
            }
        }

        FileUtils.deleteFile(item.getApkPath());


    }


    public Observable<Boolean> isInstalled(final Context context, final String packageName) {

        return Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {

                e.onNext(AppUtils.isInstalled(context, packageName));
            }
        }).compose(RxSchedulers.<Boolean>io_main());

    }

}
