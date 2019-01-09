package com.cniao5.cniao5play.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cniao5.cniao5play.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class SuggestionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SuggestionAdapter() {
        super(R.layout.suggest_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView icon = helper.getView(R.id.icon_suggestion);
        icon.setImageDrawable(new IconicsDrawable(mContext, Ionicons.Icon.ion_ios_search)
                .color(ContextCompat.getColor(mContext, R.color.white)).sizeDp(16));

        helper.setText(R.id.txt_suggestion, item);
    }
}
