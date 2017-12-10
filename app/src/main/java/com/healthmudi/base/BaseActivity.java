package com.healthmudi.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tck
 * Date: 2017/12/05 14：05
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
        initView();
        setListener();
        setViewData();

    }

    @LayoutRes
    public abstract int getLayoutId();

    public void initData() {
    }

    public void initView() {
    }

    public void setListener() {
    }

    public void setViewData() {
    }
}
