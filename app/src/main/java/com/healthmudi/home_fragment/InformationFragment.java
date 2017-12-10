package com.healthmudi.home_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * decription:消息
 * Created by tck on 2017/12/8.
 */

public class InformationFragment extends BaseFragment1 {

    public static InformationFragment newInstance() {
        InformationFragment informationFragment = new InformationFragment();

        return informationFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initView(@Nullable View view) {

    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }
}
