package com.cniao5.cniao5play.presenter;



import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.bean.Subject;
import com.cniao5.cniao5play.bean.SubjectDetail;
import com.cniao5.cniao5play.ui.BaseView;

import io.reactivex.Observable;


/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.contract
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class SubjectContract {


    public  interface   SubjectView extends BaseView {


        void showSubjects(PageBean<Subject> subjects);
        void onLoadMoreComplete();

        void showSubjectDetail(SubjectDetail detail);

    }


    public interface ISubjectModel{

        Observable<BaseBean<PageBean<Subject>>> getSubjects(int page);

        Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id);



    }
}
