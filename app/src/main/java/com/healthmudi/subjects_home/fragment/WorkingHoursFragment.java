package com.healthmudi.subjects_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.subjects_home.adapter.WorkingHoursListAdapter;
import com.healthmudi.subjects_home.five.WorkTimeSubmissionActivtiy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:工时
 * Created by tck on 2017/12/9.
 */

public class WorkingHoursFragment extends BaseFragment1 implements View.OnClickListener {

    private SmartRefreshLayout mSmartRefresh;
    private ListView mListView;

    private List<WorkingHoursListBean> mWorkingHoursListBeanList = new ArrayList<>();
    private WorkingHoursListAdapter mAdapter;

    public static WorkingHoursFragment newInstance() {
        WorkingHoursFragment workingHoursFragment = new WorkingHoursFragment();

        return workingHoursFragment;
    }

    {
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                mWorkingHoursListBeanList.add(new WorkingHoursListBean("", "", "", 0));
            } else {
                mWorkingHoursListBeanList.add(new WorkingHoursListBean("", "", "", 1));
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_working_hours;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {
        mSmartRefresh = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new WorkingHoursListAdapter(getContext(), mWorkingHoursListBeanList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_add_subjects).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
            case R.id.iv_add_subjects:
                Intent intent = new Intent(getContext(), WorkTimeSubmissionActivtiy.class);
                startActivity(intent);
                break;
        }
    }
}
