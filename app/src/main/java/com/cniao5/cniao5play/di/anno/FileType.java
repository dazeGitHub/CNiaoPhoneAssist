<<<<<<< HEAD
package com.cniao5.cniao5play.di.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/7/12.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface FileType {

    /** The name. */
    String value() default "";
}
=======
package com.cniao5.cniao5play.di.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/7/12.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface FileType {

    /** The name. */
    String value() default "";
}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
