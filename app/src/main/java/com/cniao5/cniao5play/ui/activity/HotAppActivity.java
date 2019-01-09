package com.cniao5.cniao5play.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.ui.fragment.HotAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/9.
 */

public class HotAppActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.content_layout)
    FrameLayout mContentLayout;

    @Override
    public int setLayout() {
        return R.layout.activity_hot_app;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        mToolBar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );
        mToolBar.setTitle(R.string.hot_app);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        showFragment();
    }

    private void showFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        HotAppFragment fragment = new HotAppFragment();
        ft.add(R.id.content_layout, fragment);

        ft.commit();
    }

}
