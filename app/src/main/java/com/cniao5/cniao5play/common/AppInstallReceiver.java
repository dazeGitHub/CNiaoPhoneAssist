<<<<<<< HEAD
package com.cniao5.cniao5play.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.cniao5.cniao5play.bean.AppInfoBean;
import com.cniao5.cniao5play.common.rx.RxBus;

/**
 * Created by Administrator on 2017/7/8.
 */

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();

        Log.i("AppInstallReceiver", "onReceive=" + intent.getData().getSchemeSpecificPart());

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {  //安装成功
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInfoBean(AppInfoBean.APP_INSTALL, packageName));
        }

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //卸载成功
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInfoBean(AppInfoBean.APP_UNINSTALL, packageName));
        }

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) { //替换成功
            String packageName = intent.getData().getSchemeSpecificPart();

        }

    }

=======
package com.cniao5.cniao5play.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.cniao5.cniao5play.bean.AppInfoBean;
import com.cniao5.cniao5play.common.rx.RxBus;

/**
 * Created by Administrator on 2017/7/8.
 */

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();

        Log.i("AppInstallReceiver", "onReceive=" + intent.getData().getSchemeSpecificPart());

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {  //安装成功
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInfoBean(AppInfoBean.APP_INSTALL, packageName));
        }

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //卸载成功
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInfoBean(AppInfoBean.APP_UNINSTALL, packageName));
        }

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) { //替换成功
            String packageName = intent.getData().getSchemeSpecificPart();

        }

    }

>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
}