package com.healthmudi.subjects_home.five.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * 下周工作计划
 * Created by tck
 * Date: 2017/12/19 12：54
 */

public class NextWeekPlanFragment extends BaseFragment1 {

    public static NextWeekPlanFragment newInstance() {
        NextWeekPlanFragment nextWeekPlanFragment = new NextWeekPlanFragment();

        return nextWeekPlanFragment;
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
