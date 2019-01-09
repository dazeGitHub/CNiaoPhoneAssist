package com.cniao5.cniao5play.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.common.Constant;


/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class SubjectAdapter extends BaseQuickAdapter<Subject,BaseViewHolder> {

    public SubjectAdapter() {
        super(R.layout.template_subject_imageview);
    }

    @Override
    protected void convert(BaseViewHolder helper, Subject item) {


        ImageView imageView =  helper.getView(R.id.imageview);
        String url = Constant.BASE_IMG_URL+item.getMticon();
        Glide.with(mContext).load(url).into(imageView);

    }
}
