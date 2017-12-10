package com.healthmudi.subjects_home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;

/**
 * decription:签到
 * Created by tck on 2017/12/9.
 */

public class SignFragment extends BaseFragment1 {

    public static SignFragment newInstance() {
        SignFragment signFragment = new SignFragment();

        return signFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign;
    }

    @Override
    protected void initView(@Nullable View view) {

    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }
}
