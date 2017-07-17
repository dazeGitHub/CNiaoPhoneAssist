package com.cniao5.cniao5play.data;

import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.bean.SubjectDetail;
import com.cniao5.cniao5play.data.http.ApiService;
import com.cniao5.cniao5play.presenter.SubjectContract;

import io.reactivex.Observable;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.data.model
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class SubjectModel implements SubjectContract.ISubjectModel {


    private ApiService mApiService;

    public SubjectModel(ApiService apiService) {
        this.mApiService = apiService;

    }


    @Override
    public Observable<BaseBean<PageBean<Subject>>> getSubjects(int page) {
        return mApiService.subjects(page);
    }

    @Override
    public Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id) {
        return mApiService.subjectDetail(id);
    }
}
