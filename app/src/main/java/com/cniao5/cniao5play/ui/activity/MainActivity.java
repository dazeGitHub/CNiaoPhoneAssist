package com.cniao5.cniao5play.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.User;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.font.Cniao5Font;
import com.cniao5.cniao5play.common.imageloader.GlideCircleTransform;
import com.cniao5.cniao5play.common.rx.RxBus;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerMainComponent;
import com.cniao5.cniao5play.di.module.MainModule;
import com.cniao5.cniao5play.presenter.MainPresenter;
import com.cniao5.cniao5play.presenter.contract.MainContract;
import com.cniao5.cniao5play.ui.adapter.ViewPagerAdapter;
import com.cniao5.cniao5play.ui.bean.FragmentInfo;
import com.cniao5.cniao5play.ui.fragment.CategoryFragment;
import com.cniao5.cniao5play.ui.fragment.GamesFragment;
import com.cniao5.cniao5play.ui.fragment.RecommendFragment;
import com.cniao5.cniao5play.ui.fragment.TopListFragment;
import com.cniao5.cniao5play.ui.widget.BadgeActionProvider;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {


    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    private View headerView;
    private ImageView mUserHeadView;
    private TextView mTextUserName;

    private BadgeActionProvider badgeActionProvider;


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void init() {

        boolean key_smart_install = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE).getBoolean("key_smart_install", false);

        Log.d("MainActivity", "key_smart_install=" + key_smart_install);


        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull User user) throws Exception {

                initUserHeadView(user);
            }
        });

        mPresenter.requestPermisson();
        mPresenter.getAppUpdateInfo();

    }

    private void initToolbar() {

        mToolBar.inflateMenu(R.menu.toolbar_menu);

        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_search) {

                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }

                return true;
            }
        });

        MenuItem downloadMenuItem = mToolBar.getMenu().findItem(R.id.action_download);
        badgeActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(downloadMenuItem);

        badgeActionProvider.setIcon(
                DrawableCompat.wrap(
                        new IconicsDrawable(this, Cniao5Font.Icon.cniao_download).color(
                                ContextCompat.getColor(this, R.color.white)
                        )
                )
        );

        badgeActionProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAppManagerActivity(badgeActionProvider.getBadgeNum() > 0 ?
                        Constant.AppManager.APP_UPDATE : Constant.AppManager.APP_DOWNLOAD);
            }
        });

    }

    private List<FragmentInfo> initFragments() {

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("推荐", RecommendFragment.class));
        mFragments.add(new FragmentInfo("排行", TopListFragment.class));

        mFragments.add(new FragmentInfo("游戏", GamesFragment.class));
        mFragments.add(new FragmentInfo("分类", CategoryFragment.class));

        return mFragments;

    }

    private void initTablayout() {


        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), initFragments());
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);


        mTabLayout.setupWithViewPager(mViewPager);


    }

    private void initDrawerLayout() {


        headerView = mNavigationView.getHeaderView(0);

        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_avatar);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, Cniao5Font.Icon.cniao_head).colorRes(R.color.white));

        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);


        mNavigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_loop));
        mNavigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, Cniao5Font.Icon.cniao_download));
        mNavigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline));
        mNavigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_gear_outline));

        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, Cniao5Font.Icon.cniao_shutdown));

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_logout:
                        logout();
                        break;

                    case R.id.menu_download_manager:
                        toAppManagerActivity(Constant.AppManager.APP_DOWNLOADED);
                        startActivity(new Intent(MainActivity.this, AppManagerActivity.class));
                        break;

                    case R.id.menu_app_uninstall:
                        toAppManagerActivity(Constant.AppManager.APP_UNINSTALL);
                        break;

                    case R.id.menu_app_update:
                        toAppManagerActivity(Constant.AppManager.APP_UPDATE);
                        break;

                    case R.id.menu_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;

                }


                return false;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close);

        drawerToggle.syncState();

        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    private void logout() {

        ACache aCache = ACache.get(this);

        aCache.put(Constant.TOKEN, "");
        aCache.put(Constant.USER, "");

        mUserHeadView.setImageDrawable(new IconicsDrawable(this, Cniao5Font.Icon.cniao_head).colorRes(R.color.white));
        mTextUserName.setText("未登录");

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Toast.makeText(MainActivity.this, "您已退出登录", Toast.LENGTH_LONG).show();
    }

    private void toAppManagerActivity(int position) {

        Intent intent = new Intent(MainActivity.this, AppManagerActivity.class);

        intent.putExtra(Constant.APP_MANAGER_POSITION, position);

        startActivity(new Intent(intent));

    }


    private void initUserHeadView(User user) {

        Glide.with(this).load(user.getLogo_url()).transform(new GlideCircleTransform(this)).into(mUserHeadView);

        mTextUserName.setText(user.getUsername());
    }

    private void initUser() {

        Object objUser = ACache.get(this).getAsObject(Constant.USER);

        if (objUser == null) {

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

        } else {

            User user = (User) objUser;
            initUserHeadView(user);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.getDefault().
    }


    @Override
    public void requestPermissonSuccess() {

        initToolbar();
        initDrawerLayout();
        initTablayout();
        initUser();
    }

    @Override
    public void requestPermissonFail() {
        Toast.makeText(MainActivity.this, "授权失败....", Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeAppNeedUpdateCount(int count) {
        if (count > 0) {
            badgeActionProvider.setText(count + "");
        } else {
            badgeActionProvider.hideBadge();
        }
    }
}
