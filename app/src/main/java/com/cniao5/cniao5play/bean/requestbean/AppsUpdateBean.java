package com.cniao5.cniao5play.bean.requestbean;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.data.entity.param
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppsUpdateBean {


    private String packageName;
    private String versionCode;


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
