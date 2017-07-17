package com.cniao5.cniao5play.common.apkparset;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.apkparset
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AndroidApkParser {


    public static Resources getResources(Context context, String apkPath) throws Exception {
        String PATH_AssetManager = "android.content.res.AssetManager";
        Class assetMagCls = Class.forName(PATH_AssetManager);
        Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
        Object assetMag = assetMagCt.newInstance((Object[]) null);
        Class[] typeArgs = new Class[1];
        typeArgs[0] = String.class;
        Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
                typeArgs);
        Object[] valueArgs = new Object[1];
        valueArgs[0] = apkPath;
        assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
        Resources res = context.getResources();
        typeArgs = new Class[3];
        typeArgs[0] = assetMag.getClass();
        typeArgs[1] = res.getDisplayMetrics().getClass();
        typeArgs[2] = res.getConfiguration().getClass();
        Constructor resCt = Resources.class.getConstructor(typeArgs);
        valueArgs = new Object[3];
        valueArgs[0] = assetMag;
        valueArgs[1] = res.getDisplayMetrics();
        valueArgs[2] = res.getConfiguration();
        res = (Resources) resCt.newInstance(valueArgs);
        return res;
    }


    public static AndroidApk getUninstallAPK(Context context, String path) {

        AndroidApk apk = new AndroidApk();


        PackageManager pm = context.getPackageManager();

        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        //如果info为null,则发生了包解析错误
        if (info == null) {
            Log.e("AndroidApkParser", "getUninstallAPK  PackageInfo == null");
            return null;
        }

        apk.setPackageName(info.packageName);
        apk.setApkPath(path);
        apk.setAppVersionCode(info.versionCode + "");
        apk.setAppVersionName(info.versionName);


        Resources res = null;

        try {
            // 如果apk已经安装,则可以使用info.applicationInfo.loadIcon()
            // 但没有安装只能使用反射获取Resource对象（getResource().getString(..) ）
            res = getResources(context, path);

            apk.setAppName(res.getString(info.applicationInfo.labelRes));

            Drawable icon = res.getDrawable(info.applicationInfo.icon);
            apk.setDrawable(icon);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return apk;
    }
}
