package com.healthmudi.home_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment extends BaseFragment1 implements View.OnClickListener {

    private ListView mListView;

    public static ScheduleFragment newInstance() {
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        return scheduleFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {
        mListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                break;
        }
    }
}
