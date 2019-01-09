package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.IndexBean;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerRecommendComponent;
import com.cniao5.cniao5play.di.module.RemmendModule;
import com.cniao5.cniao5play.presenter.RecommendPresenter;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;
import com.cniao5.cniao5play.ui.adapter.IndexMultipleAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;


/**
 * Created by Ivan on 16/9/26.
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenter>  implements AppInfoContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private IndexMultipleAdapter mAdatper;


    @Inject
    RxDownload mRxDownload;

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {


        DaggerRecommendComponent.builder().appComponent(appComponent)
                .remmendModule(new RemmendModule(this)).build().inject(this);
    }

    @Override
    public void init() {

        initRecycleView();
        mPresenter.requestDatas();

    }


    @Override
    public void onEmptyViewClick() {
        mPresenter.requestDatas();
    }

    private void initRecycleView(){


        //为RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());






    }


    @Override
    public void showResult(IndexBean indexBean) {

        mAdatper = new IndexMultipleAdapter(getActivity(),mRxDownload);
        mAdatper.setData(indexBean);

        mRecyclerView.setAdapter(mAdatper);

    }

    @Override
    public void onRequestPermissonSuccess() {

        mPresenter.requestDatas();
    }

    @Override
    public void onRequestPermissonError() {

        Toast.makeText(getActivity(),"你已拒绝授权",Toast.LENGTH_LONG).show();
    }


}
