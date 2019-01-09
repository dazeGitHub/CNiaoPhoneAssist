<<<<<<< HEAD
package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.common.rx.RxBus;
import com.cniao5.cniao5play.ui.adapter.SubjectAdapter;
import com.cniao5.cniao5play.ui.decoration.SpaceItemDecoration2;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/9.
 * 主题
 */

public class SubjectFragment extends BaseSubjectFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private SubjectAdapter mAdapter;

    int page = 0;

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.getSubjects(page);
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    protected void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        SpaceItemDecoration2 dividerDecoration = new SpaceItemDecoration2(5);
        mRecyclerView.addItemDecoration(dividerDecoration);

        mAdapter = new SubjectAdapter();

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Subject subject = mAdapter.getItem(position);
                RxBus.getDefault().post(subject);
            }
        });

    }

    @Override
    public void showSubjects(PageBean<Subject> subjects) {

        mAdapter.addData(subjects.getDatas());

        if (subjects.isHasMore()) {
            page++;
        }
        mAdapter.setEnableLoadMore(subjects.isHasMore());
    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.getSubjects(page);
    }
=======
package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.common.rx.RxBus;
import com.cniao5.cniao5play.ui.adapter.SubjectAdapter;
import com.cniao5.cniao5play.ui.decoration.SpaceItemDecoration2;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/9.
 * 主题
 */

public class SubjectFragment extends BaseSubjectFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private SubjectAdapter mAdapter;

    int page = 0;

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.getSubjects(page);
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    protected void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        SpaceItemDecoration2 dividerDecoration = new SpaceItemDecoration2(5);
        mRecyclerView.addItemDecoration(dividerDecoration);

        mAdapter = new SubjectAdapter();

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Subject subject = mAdapter.getItem(position);
                RxBus.getDefault().post(subject);
            }
        });

    }

    @Override
    public void showSubjects(PageBean<Subject> subjects) {

        mAdapter.addData(subjects.getDatas());

        if (subjects.isHasMore()) {
            page++;
        }
        mAdapter.setEnableLoadMore(subjects.isHasMore());
    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.getSubjects(page);
    }
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
}