<<<<<<< HEAD
package com.cniao5.cniao5play.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */


public class AppInfoPager implements Serializable {

    /**
     * hasMore : true
     * host : http://f1.market.xiaomi.com/download/
     * listApp : []
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     */

    private boolean hasMore;
    private String host;
    private String thumbnail;
    private List<AppInfo> listApp;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<AppInfo> getListApp() {
        return listApp;
    }

    public void setListApp(List<AppInfo> listApp) {
        this.listApp = listApp;
    }
}
=======
package com.cniao5.cniao5play.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */


public class AppInfoPager implements Serializable {

    /**
     * hasMore : true
     * host : http://f1.market.xiaomi.com/download/
     * listApp : []
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     */

    private boolean hasMore;
    private String host;
    private String thumbnail;
    private List<AppInfo> listApp;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<AppInfo> getListApp() {
        return listApp;
    }

    public void setListApp(List<AppInfo> listApp) {
        this.listApp = listApp;
    }
}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
