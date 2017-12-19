package com.healthmudi.subjects_home.five;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.subjects_home.five.fragment.NextWeekPlanFragment;
import com.healthmudi.subjects_home.five.fragment.SiteSituationFragment;
import com.healthmudi.subjects_home.five.fragment.ThisWeekSummaryFragment;
import com.healthmudi.subjects_home.five.fragment.WorkContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表详情界面
 * Created by tck
 * Date: 2017/12/18 16：37
 */

public class ReportFormDetailActivity extends BaseActivity {

    private ViewPager mViewpager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ReportFormDetailViewPageAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_report_form_detail;
    }

    @Override
    public void initData() {
        super.initData();
        mFragmentList.add(SiteSituationFragment.newInstance());
        mFragmentList.add(ThisWeekSummaryFragment.newInstance());
        mFragmentList.add(NextWeekPlanFragment.newInstance());
        mFragmentList.add(WorkContentFragment.newInstance());
    }

    @Override
    public void initView() {
        super.initView();
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new ReportFormDetailViewPageAdapter(getSupportFragmentManager(), mFragmentList);
        mViewpager.setAdapter(mAdapter);
    }
}
