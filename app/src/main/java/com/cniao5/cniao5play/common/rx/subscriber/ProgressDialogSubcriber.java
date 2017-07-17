package com.cniao5.cniao5play.common.rx.subscriber;

import android.content.Context;

import com.cniao5.cniao5play.common.util.ProgressDialogHandler;

import io.reactivex.disposables.Disposable;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.rx.subscriber
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public  abstract  class ProgressDialogSubcriber<T> extends ErrorHandlerSubscriber<T>  implements ProgressDialogHandler.OnProgressCancelListener {



    private ProgressDialogHandler mProgressDialogHandler;

    private Disposable mDisposable;


    public ProgressDialogSubcriber(Context context) {
        super(context);

        mProgressDialogHandler = new ProgressDialogHandler(mContext,true,this);
    }

    protected boolean isShowProgressDialog(){
        return  true;
    }

    @Override
    public void onCancelProgress() {

        mDisposable.dispose();
    }

    public void onSubscribe(Disposable d) {

        mDisposable = d;
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.showProgressDialog();
        }

    }

    @Override
    public void onComplete() {



        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }

    }

}
