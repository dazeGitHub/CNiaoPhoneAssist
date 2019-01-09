package com.cniao5.cniao5play.common.rx.subscriber;

import android.app.ProgressDialog;
import android.content.Context;

import com.cniao5.cniao5play.common.util.ProgressDialogHandler;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/6/25.
 */

public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T>
        implements ProgressDialogHandler.OnProgressCancelListener {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    private ProgressDialogHandler mProgressDialogHandler;

    private Disposable mDisposable;

    public ProgressDialogSubscriber(Context context) {
        super(context);
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(mContext, true, this);
    }

    protected boolean isShowDialog() {
        return true;
    }

//    @Override
//    public void onStart() {
//        if (isShowDialog())
//            this.mProgressDialogHandler.showProgressDialog();
//    }


    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if(isShowDialog()){
            this.mProgressDialogHandler.showProgressDialog();
        }
    }

    @Override
    public void onComplete() {
        if (isShowDialog())
            this.mProgressDialogHandler.dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (isShowDialog())
            this.mProgressDialogHandler.dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
//        unsubscribe();
        mDisposable.dispose();
    }

    private void initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("loading...");
        }
    }

}
