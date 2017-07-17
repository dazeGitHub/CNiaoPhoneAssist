package com.cniao5.cniao5play.di.module;


import com.cniao5.cniao5play.data.SearchModel;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.di.FragmentScope;
import com.cniao5.cniao5play.presenter.contract.SearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.di.module
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@Module
public class SearchModule {


    private SearchContract.SearchView mView;


    public SearchModule(SearchContract.SearchView view){

        this.mView = view;
    }

    @FragmentScope
    @Provides
    public SearchContract.ISearchModel provideModel(ApiService apiService){

        return  new SearchModel(apiService);
    }

    @FragmentScope
    @Provides
    public SearchContract.SearchView provideView(){

        return  mView;
    }
}
