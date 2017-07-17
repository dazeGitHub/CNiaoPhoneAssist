package com.cniao5.cniao5play.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.imageloader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan on 2016/12/30.
 */

public class RecomendAppAdatper extends RecyclerView.Adapter<RecomendAppAdatper.ViewHolder> {




    private Context mContext;
    private List<AppInfo> mDatas;


    private LayoutInflater mLayoutInflater;

    public RecomendAppAdatper(Context context, List<AppInfo> datas) {

        this.mDatas = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.template_recomend_app, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        AppInfo appInfo = mDatas.get(position);



        ImageLoader.load(Constant.BASE_IMG_URL+appInfo.getIcon(),holder.mImgIcon);

        holder.mTextTitle.setText(appInfo.getDisplayName());
        holder.mTextSize.setText((appInfo.getApkSize() / 1024 /1024) +" MB");

    }

    @Override
    public int getItemCount() {


        return mDatas==null?0:mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.img_icon)
        ImageView mImgIcon;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_size)
        TextView mTextSize;
        @BindView(R.id.btn_dl)
        Button mBtnDl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
