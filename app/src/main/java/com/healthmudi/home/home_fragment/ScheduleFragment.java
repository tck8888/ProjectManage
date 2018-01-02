package com.healthmudi.home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.ExitProjectManagerActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment extends BaseFragment1 implements View.OnClickListener {

    private String tag = "ScheduleFragment";
    private Map<String, String> map = new HashMap<>();

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
        map.put("month", "201712");
        getData();
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
                startActivity(new Intent(getContext(), ExitProjectManagerActivity.class));
                break;
        }
    }

    public void getData() {
        HttpRequest.getInstance().get(HttpUrlList.SCHEDULE_LIST_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(int code, String mesage) {

            }
        });
    }
}
