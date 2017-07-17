package com.cniao5.cniao5play.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.SearchResult;
import com.cniao5.cniao5play.common.rx.RxSchedulers;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerSearchComponent;
import com.cniao5.cniao5play.di.module.SearchModule;
import com.cniao5.cniao5play.presenter.SearchPresenter;
import com.cniao5.cniao5play.presenter.contract.SearchContract;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;
import com.cniao5.cniao5play.ui.adapter.SearchHistoryAdatper;
import com.cniao5.cniao5play.ui.adapter.SuggestionAdapter;
import com.cniao5.cniao5play.ui.decoration.SpaceItemDecoration2;
import com.cniao5.cniao5play.ui.widget.DividerItemDecoration;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import zlc.season.rxdownload2.RxDownload;


public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.SearchView {


    @BindView(R.id.searchTextView)
    EditText mSearchTextView;
    @BindView(R.id.action_clear_btn)
    ImageView mActionClearBtn;

    @BindView(R.id.search_bar)
    RelativeLayout mSearchBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.btn_clear)
    ImageView mBtnHistoryClear;

    @BindView(R.id.recycler_view_history)
    RecyclerView mRecyclerViewHistory;

    @BindView(R.id.layout_history)
    LinearLayout mLayoutHistory;

    @BindView(R.id.recycler_view_suggestion)
    RecyclerView mRecyclerViewSuggestion;

    @BindView(R.id.recycler_view_result)
    RecyclerView mRecyclerViewResult;

    @BindView(R.id.activity_search)
    LinearLayout mActivitySearch;

    private SuggestionAdapter mSuggestionAdapter;
    private AppInfoAdapter mAppInfoAdapter;
    private SearchHistoryAdatper mHistoryAdatper;

    @Inject
    RxDownload rxDownload;

    private Disposable disposable;
    private boolean mIsCanSearchOnSubscribe = true;

    public static final int STATUS_REQUESTING = 0;
    public static final int STATUS_FINISH = 1;
    public int status = STATUS_FINISH;

    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

        DaggerSearchComponent.builder().appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);

    }

    @Override
    public void init() {

        initView();

        initHistoryRecycleView();
        initSuggestionRecyclerView();
        initSearchResultRecycleView();

        setupSearchView();
        mPresenter.showHistory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    private void initView() {

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

        mActionClearBtn.setImageDrawable(new IconicsDrawable(this, Ionicons.Icon.ion_ios_close_empty)
                .color(ContextCompat.getColor(this, R.color.white)).sizeDp(16));


        mBtnHistoryClear.setImageDrawable(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline)
                .color(ContextCompat.getColor(this, R.color.md_grey_600)).sizeDp(16));

        RxView.clicks(mBtnHistoryClear).subscribe(new Consumer<Object>() {

            @Override
            public void accept(@NonNull Object o) throws Exception {
                mPresenter.clearSearchHistory();
            }
        });
    }


    private void initSuggestionRecyclerView() {

        mSuggestionAdapter = new SuggestionAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewSuggestion.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        mRecyclerViewSuggestion.addItemDecoration(itemDecoration);

        mRecyclerViewSuggestion.setAdapter(mSuggestionAdapter);


        mRecyclerViewSuggestion.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                search(mSuggestionAdapter.getItem(position));
            }
        });

    }

    private void initSearchResultRecycleView() {
        mAppInfoAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true).rxDownload(rxDownload).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewResult.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerViewResult.addItemDecoration(itemDecoration);

        mRecyclerViewResult.setAdapter(mAppInfoAdapter);

        mRecyclerViewResult.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void initHistoryRecycleView() {

        mHistoryAdatper = new SearchHistoryAdatper();

        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 5);
        SpaceItemDecoration2 itemd = new SpaceItemDecoration2(10);
        mRecyclerViewHistory.addItemDecoration(itemd);

        mRecyclerViewHistory.setLayoutManager(lm);
        mRecyclerViewHistory.setAdapter(mHistoryAdatper);

        mRecyclerViewHistory.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                search(mHistoryAdatper.getItem(position));
            }
        });

    }


    private void setupSearchView() {

        RxView.clicks(mActionClearBtn).subscribe(new Consumer<Object>() {

            @Override
            public void accept(@NonNull Object o) throws Exception {

                mPresenter.showHistory();
                mSearchTextView.setText("");

                mLayoutHistory.setVisibility(View.VISIBLE);
                mRecyclerViewSuggestion.setVisibility(View.GONE);
                mRecyclerViewResult.setVisibility(View.GONE);
            }
        });

        //软键盘上的搜索按钮
        RxTextView.editorActions(mSearchTextView).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

                search(mSearchTextView.getText().toString().trim());
            }
        });

        subscribeSearchView();
    }

    private void subscribeSearchView() {

        Log.i("SearchActivity", "subscribeSearchView");

        disposable = RxTextView.textChanges(mSearchTextView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<CharSequence>io_main())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length() > 0;
                    }
                })

                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@NonNull CharSequence charSequence) throws Exception {

                        if (mIsCanSearchOnSubscribe) {

                            Log.d("SearchActivity", charSequence.toString() + "，status=" + status);

                            if (charSequence.length() > 0) {
                                mActionClearBtn.setVisibility(View.VISIBLE);
                            } else {
                                mActionClearBtn.setVisibility(View.GONE);
                            }

                            mPresenter.getSuggestions(charSequence.toString().trim());

                        } else {
                            mIsCanSearchOnSubscribe = true;
                        }

                    }
                });
    }

    private void search(String keyword) {

//        如果这里直接调用这个方法而不解除订阅，会触发mSearchTextView的Consumer，导致mPresenter.getSuggestions()，
//      这里又有mPresenter.search(keyword),导致数据错乱,但是disposable.dispose()后mSearchTextView就无法再接收到事件

//        if (disposable != null) {
//            disposable.dispose();
//        }

        mIsCanSearchOnSubscribe = false;

        mSearchTextView.setText(keyword);

        mPresenter.search(keyword);
    }

    @Override
    public void showSearchHistory(List<String> list) {

        mHistoryAdatper.setNewData(list);
        mRecyclerViewSuggestion.setVisibility(View.GONE);
        mLayoutHistory.setVisibility(View.VISIBLE);
        mRecyclerViewResult.setVisibility(View.GONE);
    }

    @Override
    public void showSuggestions(List<String> list) {

        //这里用setNewData()而不用addData();因为每次都是新结果
        mSuggestionAdapter.setNewData(list);
        mRecyclerViewSuggestion.setVisibility(View.VISIBLE);
        mLayoutHistory.setVisibility(View.GONE);
        mRecyclerViewResult.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResult(SearchResult result) {

        //这里用setNewData()而不用addData();因为每次都是新结果
        mAppInfoAdapter.setNewData(result.getListApp());
        mRecyclerViewSuggestion.setVisibility(View.GONE);
        mLayoutHistory.setVisibility(View.GONE);
        mRecyclerViewResult.setVisibility(View.VISIBLE);
    }

}
