package com.cniao5.cniao5play.common.apkparset;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.common.apk_parser
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AndroidApk implements Comparable<AndroidApk> {

    private String appName;
    private String appVersionName;
    private String appVersionCode;
    private String packageName;
    private String minSdkVersion;
    private String targetSdkVersion;
    private Drawable mDrawable;
    private boolean isSystem; // 是否是系统自带的App

    private String apkPath;

    private long lastUpdateTime;


    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static AndroidApk read(Context context, String path) {

        AndroidApk androidApk = AndroidApkParser.getUninstallAPK(context, path);
        if (androidApk != null) {
            androidApk.setApkPath(path);
        }
        return androidApk;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(String minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public String getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(String targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }


//    @Override
//    public int compareTo(AndroidApk o) {
//        return (int) (o.getLastUpdateTime()-this.getLastUpdateTime());
//    }

    /**
     * 如果指定的数与参数相等返回0。
     * 如果指定的数小于参数返回 -1。
     * 如果指定的数大于参数返回 1。
     */
    @Override
    public int compareTo(AndroidApk o) {
        Log.e("AndroidApk", "compareTo appName=" + o.getAppName() + " isSystem =" + isSystem);
        return (this.isSystem() && !o.isSystem()) ? 1 : -1;
    }

}
