package com.healthmudi.home_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment extends BaseFragment1 {

    public static ScheduleFragment newInstance() {
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        return scheduleFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initView(@Nullable View view) {

    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }
}
