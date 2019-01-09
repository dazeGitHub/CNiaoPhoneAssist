package com.cniao5.cniao5play.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.imageloader.ImageLoader;
import com.cniao5.cniao5play.ui.widget.DownloadButtonController;
import com.cniao5.cniao5play.ui.widget.DownloadProgressButton;

import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class DownloadingAdapter extends BaseQuickAdapter<DownloadRecord,BaseViewHolder> {




    private DownloadButtonController mDownloadButtonController;

    public DownloadingAdapter(RxDownload rxDownload) {
        super(R.layout.template_app_downloading);
        mDownloadButtonController = new DownloadButtonController(rxDownload);

        openLoadAnimation();
    }



    @Override
    protected void convert(BaseViewHolder helper, DownloadRecord item) {


        AppInfo appInfo = mDownloadButtonController.downloadRecord2AppInfo(item);


        ImageLoader.load(Constant.BASE_IMG_URL+appInfo.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));
        helper.setText(R.id.txt_app_name,appInfo.getDisplayName());


        helper.addOnClickListener(R.id.btn_download);
        View viewBtn  = helper.getView(R.id.btn_download);

        if (viewBtn instanceof  DownloadProgressButton){

            DownloadProgressButton btn = (DownloadProgressButton) viewBtn;
            mDownloadButtonController.handDownloadBtnByDownRecord(btn,item);
        }


    }







}
