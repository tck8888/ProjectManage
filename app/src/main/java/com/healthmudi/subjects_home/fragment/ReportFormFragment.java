package com.healthmudi.subjects_home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * decription:报表
 * Created by tck on 2017/12/9.
 */

public class ReportFormFragment extends BaseFragment1 {

    public static ReportFormFragment newInstance() {
        ReportFormFragment reportFormFragment = new ReportFormFragment();

        return reportFormFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_form;
    }

    @Override
    protected void initView(@Nullable View view) {

    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }
}
