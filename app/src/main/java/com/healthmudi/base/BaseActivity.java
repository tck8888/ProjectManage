package com.healthmudi.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.healthmudi.R;

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

    public void hideSoftKeyBord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constant.IS_DEBUG) {
            System.out.println("=======" + getClass().getSimpleName());
        }
    }

    public void activityFinish() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
