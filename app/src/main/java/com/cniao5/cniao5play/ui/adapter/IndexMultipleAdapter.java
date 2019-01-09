package com.cniao5.cniao5play.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cniao5.cniao5play.AppApplication;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.Banner;
import com.cniao5.cniao5play.bean.IndexBean;
import com.cniao5.cniao5play.common.imageloader.ImageLoader;
import com.cniao5.cniao5play.ui.activity.AppDetailActivity;
import com.cniao5.cniao5play.ui.activity.HotAppActivity;
import com.cniao5.cniao5play.ui.activity.SubjectActivity;
import com.cniao5.cniao5play.ui.widget.BannerLayout;
import com.cniao5.cniao5play.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zlc.season.rxdownload2.RxDownload;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class IndexMultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    public static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;


    private IndexBean mIndexBean;

    private LayoutInflater mLayoutInflater;

    private Context mContext;


    private RxDownload mRxDownload;

    public IndexMultipleAdapter(Context context, RxDownload rxDownload) {

        mContext = context;
        this.mRxDownload = rxDownload;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(IndexBean indexBean) {

        mIndexBean = indexBean;
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_ICON;
        } else if (position == 2) {
            return TYPE_APPS;
        } else if (position == 3) {
            return TYPE_GAMES;
        }

        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_BANNER) {

            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.template_banner, parent, false));
        } else if (viewType == TYPE_ICON) {

            return new NavIconViewHolder(mLayoutInflater.inflate(R.layout.template_nav_icon, parent, false));

        } else if (viewType == TYPE_APPS) {

            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recyleview_with_title, null, false), TYPE_APPS);
        } else if (viewType == TYPE_GAMES) {

            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recyleview_with_title, null, false), TYPE_GAMES);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (position == 0) {

            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

            List<Banner> banners = mIndexBean.getBanners();
            List<String> urls = new ArrayList<>(banners.size());

            for (Banner banner : banners) {

                urls.add(banner.getThumbnail());
            }

            bannerViewHolder.mBanner.setViewUrls(urls);

            bannerViewHolder.mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    banners.get(position)
                }
            });
        } else if (position == 1) {

            NavIconViewHolder bannerViewHolder = (NavIconViewHolder) holder;

            bannerViewHolder.mLayoutHotApp.setOnClickListener(this);
            bannerViewHolder.mLayoutHotGame.setOnClickListener(this);
            bannerViewHolder.mLayoutHotSubject.setOnClickListener(this);

        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;

            //Adapter就在这创建，别想着在onCreate的时候创建，如果多个类型，多个ViewHolder是同一种类型，导致一个全局adapter引用多次被赋值,导致还没滑到上一个条目图片闪动异常,
            //即使使用setTag页白扯，因为不是异步问题,
            //如果在这里使用holder.getAdapter().notifyDataSetChange()也不行，RecyclerView会报错：不能在滑动的时候调用notifyDataSetChange方法！
            //所以单个类型在onCreateViewHolder()的时候调用时暂时可以的，但是多个类型只能如下创建然后setAdapter()

            final AppInfoAdapter appInfoAdapter = AppInfoAdapter.builder()
                    .showBrief(true)
                    .showCategoryName(false)
                    .showPosition(false)
                    .rxDownload(mRxDownload)
                    .build();

            if (viewHolder.type == TYPE_APPS) {
                viewHolder.mText.setText("热门应用");
                appInfoAdapter.addData(mIndexBean.getRecommendApps());
            } else {
                viewHolder.mText.setText("热门游戏");
                appInfoAdapter.addData(mIndexBean.getRecommendGames());
            }

            viewHolder.mRecyclerView.setAdapter(appInfoAdapter);

            viewHolder.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                    AppInfo appInfo = appInfoAdapter.getItem(position);
                    AppApplication application = (AppApplication) ((Activity) mContext).getApplication();
                    application.setView(view);

                    Intent intent = new Intent(mContext, AppDetailActivity.class);
                    intent.putExtra("appinfo", appInfo);
                    mContext.startActivity(intent);
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_hot_app) {
            mContext.startActivity(new Intent(mContext, HotAppActivity.class));
        } else if (v.getId() == R.id.layout_hot_subject) {

            mContext.startActivity(new Intent(mContext, SubjectActivity.class));
        }
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.banner)
        BannerLayout mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mBanner.setImageLoader(new ImgLoader());
        }
    }

    class NavIconViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.layout_hot_app)
        LinearLayout mLayoutHotApp;
        @BindView(R.id.layout_hot_game)
        LinearLayout mLayoutHotGame;
        @BindView(R.id.layout_hot_subject)
        LinearLayout mLayoutHotSubject;

        NavIconViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    class AppViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;


        int type;

        public AppViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.type = type;

            //这个方法是在onCreateViewHolder的时候调用的,从而防止重复设置ItemDecoration导致间距过大
            initRecyclerView();


        }

        private void initRecyclerView() {

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext) {

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);

            mRecyclerView.addItemDecoration(itemDecoration);

        }
    }

    class ImgLoader implements BannerLayout.ImageLoader {


        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path, imageView);
        }
    }

}
