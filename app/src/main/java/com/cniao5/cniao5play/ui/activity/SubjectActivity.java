package com.cniao5.cniao5play.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.common.rx.RxBus;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.ui.fragment.SubjectDetailFragment;
import com.cniao5.cniao5play.ui.fragment.SubjectFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SubjectActivity extends BaseActivity {

    public static final int FRAGMENT_SUBJECT = 0;
    public static final int FRAGMENT_DETAIL = 1;

    private int fragmentIndex = FRAGMENT_SUBJECT;

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    SubjectFragment mSubjectFragment;
    SubjectDetailFragment mDetailFragment;
    private FragmentManager mFragmentManager;


    @Override
    public int setLayout() {
        return R.layout.template_toolbar_framelayout;
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

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handNavigation();
            }
        });

        mFragmentManager = getSupportFragmentManager();

        showSubjectFragment();

        showSubjectDetailFragmentRxBus();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();    //需要把这个注释了,否则一调用super.onBackPressed()直接就回到主页了
        handNavigation();
    }

    private void handNavigation() {
        if (fragmentIndex == FRAGMENT_SUBJECT) {
            finish();
        } else {
            showSubjectFragment();
        }
    }

    private void showSubjectFragment() {

        fragmentIndex = FRAGMENT_SUBJECT;
        mToolBar.setTitle("主题");
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        hideFragment(ft);

        if (mSubjectFragment == null) {
            mSubjectFragment = new SubjectFragment();
            ft.add(R.id.content, mSubjectFragment);
        } else {
            ft.show(mSubjectFragment);
        }
        ft.commit();
    }

    private void showSubjectDetailFragmentRxBus() {
        RxBus.getDefault().toObservable(Subject.class).subscribe(new Consumer<Subject>() {
            @Override
            public void accept(@NonNull Subject subject) throws Exception {
                showSubjectDetailFragment(subject);
            }
        });
    }

    private void showSubjectDetailFragment(Subject subject) {

        fragmentIndex = FRAGMENT_DETAIL;
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (mDetailFragment != null) {
            ft.remove(mDetailFragment);
        }

        mDetailFragment = new SubjectDetailFragment();
        mDetailFragment.setArgs(subject);
        ft.add(R.id.content, mDetailFragment);


        ft.commit();

        mToolBar.setTitle(subject.getTitle());
    }

    private void hideFragment(FragmentTransaction ft) {
        if (mSubjectFragment != null) {
            ft.hide(mSubjectFragment);
        }
        if (mDetailFragment != null) {
            ft.hide(mDetailFragment);
        }
    }

}
