package com.cniao5.cniao5play.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.common.Constant;
import com.cniao5.cniao5play.common.rx.RxSchedulers;
import com.cniao5.cniao5play.common.util.ACache;
import com.cniao5.cniao5play.common.util.DataCleanManager;

import cn.qqtheme.framework.picker.FilePicker;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/10.
 */

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        //可以设置sharedPreference目录名
//      getPreferenceManager().setSharedPreferencesName();

        initData();
    }

    private void initData() {
        initClearCachePref();
        initDownloadDirPref();
    }

    private void initDownloadDirPref() {
        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_apk_download_dir));
        final String dir = ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR);
        pref.setSummary(dir);

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                dirChoose(pref, dir);
                return false;
            }
        });

    }

    private void dirChoose(final Preference pref, String defualtDir) {

        FilePicker fp = new FilePicker(getActivity(), FilePicker.DIRECTORY);

        fp.setRootPath(defualtDir);
        fp.setItemHeight(50);

        fp.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {

                pref.setSummary(currentPath);
                ACache.get(getActivity()).put(Constant.APK_DOWNLOAD_DIR, currentPath);
            }
        });

        fp.show();

    }

    private void initClearCachePref() {

        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_clear_cache));

        try {
            //显示缓存大小
            pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                clearCache().subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        Toast.makeText(SettingFragment.this.getActivity(), "清理成功", Toast.LENGTH_LONG).show();
                        pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));

                    }
                });
                return true;
            }
        });


    }

    private Observable clearCache() {

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                DataCleanManager.cleanFiles(getActivity());
                DataCleanManager.cleanInternalCache(getActivity());
                DataCleanManager.deleteFolderFile(ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR), false);

                e.onNext(1);
                e.onComplete();

            }
        }).compose(RxSchedulers.<Integer>io_main());

    }

}
