package com.cniao5.cniao5play.bean;

import java.io.Serializable;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.bean
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class BaseBean<T>  extends BaseEntity {


    public static final int SUCCESS=1;
    private int status;

    private  String message;

    private T data;


    public boolean success(){

        return  (status==SUCCESS);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
