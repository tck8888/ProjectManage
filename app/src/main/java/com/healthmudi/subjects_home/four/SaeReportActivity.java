package com.healthmudi.subjects_home.four;

import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;

/**
 * decription:sae上报
 * Created by tck on 2017/12/11.
 */

public class SaeReportActivity extends BaseActivity implements View.OnClickListener {



    @Override
    public int getLayoutId() {
        return R.layout.activity_sae_report;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();

    }



    @Override
    public void setViewData() {
        super.setViewData();

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;

            case R.id.iv_check_mark:
                break;


        }
    }

}
