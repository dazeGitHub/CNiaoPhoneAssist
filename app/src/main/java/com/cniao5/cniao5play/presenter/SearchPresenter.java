package com.cniao5.cniao5play.presenter;

import android.text.TextUtils;

import com.cniao5.cniao5play.bean.SearchResult;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.rx.RxHttpReponseCompat;
import com.cniao5.cniao5play.common.rx.subscriber.ProgressSubcriber;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.presenter.contract.SearchContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/10.
 */


public class SearchPresenter extends BasePresenter<SearchContract.ISearchModel, SearchContract.SearchView> {

    private List<String> historyList;
    private Gson mGson;

    @Inject
    public SearchPresenter(SearchContract.ISearchModel iSearchModel, SearchContract.SearchView searchtView, Gson gson) {
        super(iSearchModel, searchtView);

        this.mGson = gson;

        String searchJsonStr = ACache.get(mContext).getAsString(Constant.SEARCH_HISTORY);
        if (!TextUtils.isEmpty(searchJsonStr)) {
            historyList = gson.fromJson(searchJsonStr, new TypeToken<List<String>>() {
            }.getType());
        }
    }


    public void getSuggestions(String keyword) {


        mModel.getSuggestion(keyword)
                .compose(RxHttpReponseCompat.<List<String>>compatResult())
                .subscribe(new ProgressSubcriber<List<String>>(mContext, mView) {
                    @Override
                    public void onNext(List<String> suggestions) {

                        mView.showSuggestions(suggestions);
                    }
                });

    }


    public void search(String keyword) {

        saveSearchHistory(keyword);

        mModel.search(keyword)
                .compose(RxHttpReponseCompat.<SearchResult>compatResult())
                .subscribe(new ProgressSubcriber<SearchResult>(mContext, mView) {
                    @Override
                    public void onNext(SearchResult searchResult) {
                        mView.showSearchResult(searchResult);
                    }
                });

    }

    private void saveSearchHistory(String keyword) {
        if (historyList == null) {
            historyList = new ArrayList<>();
        }

        historyList.add(keyword);
    }

    public void showHistory() {

//        historyList.add("地图");
//        historyList.add("KK");

        if (historyList != null) {
            mView.showSearchHistory(historyList);
        }
    }

    public void onStop() {

        //将历史记录保存到文件中
        if (historyList != null) {
            ACache.get(mContext).put(Constant.SEARCH_HISTORY, mGson.toJson(historyList));
        }
    }

    public void clearSearchHistory() {
        historyList = null;
        ACache.get(mContext).put(Constant.SEARCH_HISTORY, "");
        mView.showSearchHistory(new ArrayList<String>());
    }
}
