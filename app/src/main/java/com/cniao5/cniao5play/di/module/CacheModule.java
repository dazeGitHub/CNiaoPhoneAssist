<<<<<<< HEAD
package com.cniao5.cniao5play.di.module;

import com.cniao5.cniao5play.data.http.Providers;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by Administrator on 2017/7/11.
 */

@Module
public class CacheModule {


    @Provides
    @Singleton
    public RxCache providePxCache(File cacheDir, Gson gson) {
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker(gson));

    }

    @Provides
    @Singleton
    public Providers provideProviders(RxCache rxCache) {
        return rxCache.using(Providers.class);
    }

}
=======
package com.cniao5.cniao5play.di.module;

import com.cniao5.cniao5play.data.http.Providers;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by Administrator on 2017/7/11.
 */

@Module
public class CacheModule {


    @Provides
    @Singleton
    public RxCache providePxCache(File cacheDir, Gson gson) {
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker(gson));

    }

    @Provides
    @Singleton
    public Providers provideProviders(RxCache rxCache) {
        return rxCache.using(Providers.class);
    }

}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
