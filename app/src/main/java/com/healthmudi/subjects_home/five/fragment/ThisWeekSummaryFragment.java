package com.healthmudi.subjects_home.five.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * 本周工作总结
 * Created by tck
 * Date: 2017/12/19 12：54
 */

public class ThisWeekSummaryFragment extends BaseFragment1 {

    public static ThisWeekSummaryFragment newInstance() {
        ThisWeekSummaryFragment thisWeekSummaryFragment = new ThisWeekSummaryFragment();

        return thisWeekSummaryFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_site_situation;
    }

    @Override
    protected void initView(@Nullable View view) {

    }
}
