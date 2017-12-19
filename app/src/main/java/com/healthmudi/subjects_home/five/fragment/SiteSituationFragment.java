package com.healthmudi.subjects_home.five.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.ReportFormDetailBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.five.fragment.adapter.SiteSituationListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 目前Site现状
 * Created by tck
 * Date: 2017/12/19 12：54
 */

public class SiteSituationFragment extends BaseFragment1 {

    private AutoListView autoListView;
    private List<ReportFormDetailBean> mReportFormDetailBeanList = new ArrayList<>();

    public static SiteSituationFragment newInstance() {
        SiteSituationFragment siteSituationFragment = new SiteSituationFragment();
        return siteSituationFragment;
    }

    {
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
        mReportFormDetailBeanList.add(new ReportFormDetailBean("受试者筛选人数", "23"));
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

        autoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        View headView = View.inflate(getContext(), R.layout.report_form_head, null);
        autoListView.addHeaderView(headView);
        autoListView.setAdapter(new SiteSituationListAdapter(getContext(), mReportFormDetailBeanList));
    }
}
