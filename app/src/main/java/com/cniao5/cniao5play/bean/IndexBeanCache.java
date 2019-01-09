package com.cniao5.cniao5play.bean;

/**
 * Created by Administrator on 2017/7/11.
 */

public class IndexBeanCache {

    public static final int SUCCESS = 1;
    private int status;

    private String message;
    private IndexBean data;


    public boolean success() {

        return (status == SUCCESS);
    }

    public IndexBean getData() {
        return data;
    }

    public void setData(IndexBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    @Override
    public String toString() {
        return "IndexBeanCache{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
