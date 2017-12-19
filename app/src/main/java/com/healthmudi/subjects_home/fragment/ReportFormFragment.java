package com.healthmudi.subjects_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.ReportFormListBean;
import com.healthmudi.subjects_home.five.AddReportFormActivity;
import com.healthmudi.subjects_home.five.ReportFormDetailActivity;
import com.healthmudi.subjects_home.five.ReportFormListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:报表
 * Created by tck on 2017/12/9.
 */

public class ReportFormFragment extends BaseFragment1 implements View.OnClickListener {

    private ListView listView;
    private SmartRefreshLayout mRefreshLayout;

    private List<ReportFormListBean> mReportFormListBeen = new ArrayList<>();
    private ReportFormListAdapter mAdapter;

    {
        mReportFormListBeen.add(new ReportFormListBean("恒瑞G201项目报表", "恒瑞G201项目", "2017-10-30~2017-11-05", "0"));
        mReportFormListBeen.add(new ReportFormListBean("市一眼科数据录入项目报表", "市一眼科数据录入项目", "2017-10-30~2017-11-05", "0"));
        mReportFormListBeen.add(new ReportFormListBean("高甘油三脂血症报表", "高甘油三脂血症", "2017-10-30~2017-11-05", "0"));
        mReportFormListBeen.add(new ReportFormListBean("高血压报表", "高血压项目", "2017-10-30~2017-11-05", "1"));
        mReportFormListBeen.add(new ReportFormListBean("报表名称", "恒瑞G201项目", "2017-11-20~2017-11-27", "1"));

    }

    public static ReportFormFragment newInstance() {
        ReportFormFragment reportFormFragment = new ReportFormFragment();

        return reportFormFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_form;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {

        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        listView = (ListView) view.findViewById(R.id.list_view);
        View headView = View.inflate(getContext(), R.layout.head_view_layout, null);
        listView.addHeaderView(headView);
        mAdapter = new ReportFormListAdapter(getContext(), mReportFormListBeen);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_add_report_form).setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ReportFormDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
            case R.id.iv_add_report_form:
                Intent intent = new Intent(getContext(), AddReportFormActivity.class);
                startActivity(intent);
                break;
        }
    }
}
