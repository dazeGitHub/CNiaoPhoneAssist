package com.cniao5.cniao5play.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.Category;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerCategoryComponent;
import com.cniao5.cniao5play.di.module.CategoryModule;
import com.cniao5.cniao5play.presenter.CateogryPresenter;
import com.cniao5.cniao5play.presenter.contract.CategoryContract;
import com.cniao5.cniao5play.ui.activity.CategoryAppActivity;
import com.cniao5.cniao5play.ui.adapter.CategoryAdapter;
import com.cniao5.cniao5play.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan on 16/9/26.
 */

public class CategoryFragment extends ProgressFragment<CateogryPresenter> implements CategoryContract.CategoryView {


    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private CategoryAdapter mAdapter;

    @Override
    public void init() {

        initRecyclerView();

        mPresenter.getAllCategory();
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {


        DaggerCategoryComponent.builder().appComponent(appComponent).categoryModule(new CategoryModule(this))
                .build().inject(this);
    }


    private void initRecyclerView(){


        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()) );

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);

        mRecycleView.addItemDecoration(itemDecoration);
        mAdapter = new CategoryAdapter();

        mRecycleView.setAdapter(mAdapter);

        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);

                intent.putExtra(Constant.CATEGORY,mAdapter.getData().get(position));

                startActivity(intent);

            }
        });

    }

    @Override
    public void showData(List<Category> categories) {

        mAdapter.addData(categories);
    }
}
