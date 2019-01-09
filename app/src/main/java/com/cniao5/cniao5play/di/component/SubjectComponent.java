<<<<<<< HEAD
package com.cniao5.cniao5play.di.component;

import com.cniao5.cniao5play.di.FragmentScope;
import com.cniao5.cniao5play.di.module.SubjectModule;
import com.cniao5.cniao5play.ui.fragment.BaseSubjectFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/7/9.
 */


@FragmentScope
@Component(modules = SubjectModule.class,dependencies= AppComponent.class)
public interface SubjectComponent {

    void  inject(BaseSubjectFragment fragment);
}
=======
package com.cniao5.cniao5play.di.component;

import com.cniao5.cniao5play.di.FragmentScope;
import com.cniao5.cniao5play.di.module.SubjectModule;
import com.cniao5.cniao5play.ui.fragment.BaseSubjectFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/7/9.
 */


@FragmentScope
@Component(modules = SubjectModule.class,dependencies= AppComponent.class)
public interface SubjectComponent {

    void  inject(BaseSubjectFragment fragment);
}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
