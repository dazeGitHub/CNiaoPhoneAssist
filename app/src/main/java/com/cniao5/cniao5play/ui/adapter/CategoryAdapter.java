package com.cniao5.cniao5play.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.Category;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.imageloader.ImageLoader;


/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class CategoryAdapter extends BaseQuickAdapter<Category,BaseViewHolder> {





    public CategoryAdapter() {

        super(R.layout.template_category);

    }




    @Override
    protected void convert(BaseViewHolder helper, Category item) {

        helper.setText(R.id.text_name,item.getName());

        ImageLoader.load(Constant.BASE_IMG_URL+item.getIcon(), (ImageView) helper.getView(R.id.img_icon));
    }





}
