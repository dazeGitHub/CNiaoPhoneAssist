<<<<<<< HEAD
package com.cniao5.cniao5play.ui.fragment;

import com.cniao5.cniao5play.presenter.AppInfoPresenter;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;

/**
 * Created by Administrator on 2017/7/9.
 */

public class HotAppFragment extends BaseAppInfoFragment {

    @Override
    int type() {
        return AppInfoPresenter.HOT_APP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).rxDownload(mRxDownload).build();
    }


}
=======
package com.cniao5.cniao5play.ui.fragment;

import com.cniao5.cniao5play.presenter.AppInfoPresenter;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;

/**
 * Created by Administrator on 2017/7/9.
 */

public class HotAppFragment extends BaseAppInfoFragment {

    @Override
    int type() {
        return AppInfoPresenter.HOT_APP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).rxDownload(mRxDownload).build();
    }


}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
