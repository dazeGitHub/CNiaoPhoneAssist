package com.cniao5.cniao5play.data;

import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.IndexBean;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.data.http.ApiService;

import io.reactivex.Observable;

/**
 * Created by Ivan on 2017/1/3.
 */

public class AppInfoModel extends BaseModel{


//    public AppInfoModel(ApiService apiService, Providers providers) {
//        super(apiService, providers);
//    }

    public AppInfoModel(ApiService apiService) {
        super(apiService);
    }

    public Observable<BaseBean<IndexBean>> index() {

        return mApiService.index();
    }

//    public Observable<IndexBeanCache> getIndexBeanCache() {
//        return mProviders.getIndexInfo(mApiService.indexBeanCache());
//    }

//    public Observable<IndexBeanCache> getIndexCache(){
//
//        //getIndexInfo$d$d$d$$g$g$g$
//
//        return mCacheProvider.getIndexInfo(mApiService.indexCache())
//                .flatMap(new Function<Reply<IndexBeanCache>,ObservableSource<IndexBeanCache>>()){
//                    @Override
//                    public Observable<IndexBeanCache> apply(@NonNull Reply<IndexBeanCache> indexBeanCacheReply) throw Exception{
//                    return Observable.just(indexBeanCacheReply.getData());
//                }
//            });
//        }
//    }

    public Observable<BaseBean<PageBean<AppInfo>>> topList(int page) {

        return mApiService.topList(page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> games(int page) {

        return mApiService.games(page);
    }


    public Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppsByCategory(int categoryid, int page) {

        return mApiService.getFeaturedAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getTopListAppsByCategory(int categoryid, int page) {

        return mApiService.getTopListAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getNewListAppsByCategory(int categoryid, int page) {

        return mApiService.getNewListAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<AppInfo>> getAppDetail(int id) {

        return mApiService.getAppDetail(id);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getHotApps(int page) {

        return mApiService.getHotApps(page);
    }

}
