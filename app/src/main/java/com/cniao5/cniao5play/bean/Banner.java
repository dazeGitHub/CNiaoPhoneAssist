package com.cniao5.cniao5play.bean;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.bean
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class Banner {


    /**
     * thumbnail : http://t4.market.mi-img.com/thumbnail/jpeg/l750/AppStore/0ff69546de24355c8a484aafc27222e230f41f76f
     * action : subject
     * id : 169136
     */

    private String thumbnail;
    private String action;
    private String id;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
