package com.cniao5.cniao5play.bean;

/**
 * Created by Administrator on 2017/7/8.
 */

public class AppInfoBean extends BaseEntity {

    public static final int APP_UNINSTALL = 0x01;
    public static final int APP_INSTALL = 0x01;

    private int mAppState;

    String packageName;

    public AppInfoBean(int appState, String packageName) {
        mAppState = appState;
        this.packageName = packageName;
    }

    public int getAppState() {
        return mAppState;
    }

    public void setAppState(int appState) {
        mAppState = appState;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
