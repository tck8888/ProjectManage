package com.healthmudi.subjects_home.five.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * 机构立项、伦理、合同、启动会等
 * Created by tck
 * Date: 2017/12/19 12：54
 */

public class WorkContentFragment extends BaseFragment1 {

    public static WorkContentFragment newInstance() {
        WorkContentFragment workContentFragment = new WorkContentFragment();

        return workContentFragment;
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
