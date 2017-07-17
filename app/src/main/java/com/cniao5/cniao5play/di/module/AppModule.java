package com.cniao5.cniao5play.di.module;

import android.app.Application;

import com.cniao5.cniao5play.di.anno.FileType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.File;
import java.lang.reflect.Type;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di.module
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@Module
public class AppModule {


    private Application mApplication;


    public AppModule(Application application) {

        this.mApplication = application;

    }

    @Provides
    @Singleton
    public Application provideApplication() {


        return mApplication;
    }


    @Provides
    @Singleton
    public Gson provideGson() {

        Gson gson = new GsonBuilder()

                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());

                        return new JsonPrimitive(src);
                    }
                })
                .create();

        return gson;

    }

    @Singleton
    @Provides
    @FileType("download")
    File provideDownloadDir(){
        return null;
    }

    @Singleton
    @Provides
    @Named("cache")
    File provideCacheProvider(Application application){

        return application.getCacheDir();
    }

}
