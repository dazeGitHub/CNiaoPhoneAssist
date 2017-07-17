package com.cniao5.cniao5play.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.ui.adapter.ViewPagerAdapter;
import com.cniao5.cniao5play.ui.bean.FragmentInfo;
import com.cniao5.cniao5play.ui.fragment.DownloadedFragment;
import com.cniao5.cniao5play.ui.fragment.DownloadingFragment;
import com.cniao5.cniao5play.ui.fragment.InstalledAppAppFragment;
import com.cniao5.cniao5play.ui.fragment.UpgradeAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AppManagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private int position;

    private void initToolbar() {


        mToolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setTitle(R.string.download_manager);
    }


    @Override
    public int setLayout() {
        return R.layout.activity_download_manager;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        position = getIntent().getIntExtra(Constant.APP_MANAGER_POSITION, 0);

        initToolbar();
        initTablayout();
    }

    private void initTablayout() {

        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),initFragments());
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(position);
        mTabLayout.getTabAt(position).select();
    }


    private List<FragmentInfo> initFragments(){

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("下载",DownloadingFragment.class));
        mFragments.add(new FragmentInfo ("已完成", DownloadedFragment.class));
        mFragments.add(new FragmentInfo ("升级", UpgradeAppFragment.class));
        mFragments.add(new FragmentInfo ("已安装", InstalledAppAppFragment.class));

        return  mFragments;

    }
}
