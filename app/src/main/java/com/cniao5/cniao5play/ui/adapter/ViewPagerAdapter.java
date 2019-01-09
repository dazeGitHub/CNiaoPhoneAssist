package com.cniao5.cniao5play.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cniao5.cniao5play.ui.bean.FragmentInfo;
<<<<<<< HEAD

=======
import com.cniao5.cniao5play.ui.fragment.CategoryFragment;
import com.cniao5.cniao5play.ui.fragment.GamesFragment;
import com.cniao5.cniao5play.ui.fragment.TopListFragment;
import com.cniao5.cniao5play.ui.fragment.RecommendFragment;

import java.util.ArrayList;
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
import java.util.List;

/**
 * Created by Ivan on 2016/12/8.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

<<<<<<< HEAD
    private List<FragmentInfo> mFragments;

=======



    private List<FragmentInfo> mFragments;


>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
    public ViewPagerAdapter(FragmentManager fm,List<FragmentInfo> fragments) {
        super(fm);

//        initFragments();

        mFragments = fragments;
    }

<<<<<<< HEAD
=======





>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
    @Override
    public Fragment getItem(int position) {


        try {
            return (Fragment) mFragments.get(position).getFragment().newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  null;

//        Fragment fragment = null;
//        switch (position){
//
//            case 0:
//                fragment = new RecommendFragment();
//                break;
//
//            case 1:
//                fragment = new TopListFragment();
//                break;
//
//            case 2:
//                fragment = new GamesFragment();
//                break;
//
//            case 3:
//                fragment = new CategoryFragment();
//                break;
//
//        }
//
//        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
