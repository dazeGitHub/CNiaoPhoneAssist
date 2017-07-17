package com.cniao5.cniao5play.common;

import android.app.Application;
import android.os.Environment;

import com.cniao5.cniao5play.AppApplication;
import com.cniao5.cniao5play.common.util.ACache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zlc.season.rxdownload2.RxDownload;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@Module
public class DownloadModule {

    @Provides
    @Singleton
    public RxDownload provideRxDownload(Application application, Retrofit retrofit,File downDir){


        ACache.get(application).put(Constant.APK_DOWNLOAD_DIR,downDir.getPath());

       return RxDownload.getInstance(application)
                .defaultSavePath(downDir.getPath())
                .retrofit(retrofit)
                .maxDownloadNumber(10)
                .maxThread(10);
    }


    @Singleton
    @Provides
//    @FileType("download")
    File provideDownloadDir(){

        return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);

    }



}
