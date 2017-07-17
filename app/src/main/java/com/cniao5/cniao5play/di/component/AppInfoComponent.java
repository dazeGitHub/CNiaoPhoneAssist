package com.cniao5.cniao5play.di.component;

import com.cniao5.cniao5play.di.FragmentScope;
import com.cniao5.cniao5play.di.module.AppInfoModule;
import com.cniao5.cniao5play.ui.fragment.BaseAppInfoFragment;

import dagger.Component;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@FragmentScope
@Component(modules = {AppInfoModule.class}, dependencies = AppComponent.class)
public interface AppInfoComponent {

    void injectTopListFragment(BaseAppInfoFragment fragment);
//    void injectGamesFragment(GamesFragment fragment);
//    void injectCategoryAppFragment(CategoryAppFragment fragment);
}
