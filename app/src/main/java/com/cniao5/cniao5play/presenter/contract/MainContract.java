package com.cniao5.cniao5play.presenter.contract;



import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.BaseBean;
import com.cniao5.cniao5play.bean.requestbean.AppsUpdateBean;
import com.cniao5.cniao5play.ui.BaseView;

import java.util.List;

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

public class MainContract {


    public  interface   MainView extends BaseView {

        void requestPermissonSuccess();
        void requestPermissonFail();

        void changeAppNeedUpdateCount(int count);
    }


    public interface IMainModel{


        Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean param);

    }
}
