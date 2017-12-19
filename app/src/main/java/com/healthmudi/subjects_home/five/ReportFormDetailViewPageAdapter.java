package com.healthmudi.subjects_home.five;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/19 12：49
 */

public class ReportFormDetailViewPageAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragmentList;

    public ReportFormDetailViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public ReportFormDetailViewPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
