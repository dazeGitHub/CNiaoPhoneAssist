package com.cniao5.cniao5play;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.cniao5.cniao5play.common.font.Cniao5Font;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.di.component.DaggerAppComponent;
import com.cniao5.cniao5play.di.module.AppModule;
import com.cniao5.cniao5play.di.module.HttpModule;
import com.mikepenz.iconics.Iconics;
import com.umeng.analytics.MobclickAgent;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppApplication extends Application {




    private View mView;


    private AppComponent mAppComponent;



    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }

    public AppComponent getAppComponent(){

        return mAppComponent;
    }


    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        initUMeng();

        //only required if you add a custom or generic font on your own
        Iconics.init(getApplicationContext());
        //register custom fonts like this (or also provide a font definition file)
        Iconics.registerFont(new Cniao5Font());

        mAppComponent= DaggerAppComponent.builder().appModule(new AppModule(this))
                .httpModule(new HttpModule()).build();
    }

    private void initUMeng(){

//        如果希望在代码中配置Appkey、Channel、Token（Dplus）等信息，请在程序入口处调用如下方法：

//        构造意义：
//        String appkey:官方申请的Appkey
//        String channel: 渠道号
//        EScenarioType eType: 场景模式，包含统计、游戏、统计盒子、游戏盒子
//        Boolean isCrashEnable: 可选初始化. 是否开启crash模式

        //UMAnalyticsConfig(Context context, String appkey, String channelId, EScenarioType eType)
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,"5965cc0aaed17916c8000825","wandoujia"));

    }


}
