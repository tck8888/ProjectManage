package com.healthmudi.subjects_home.four;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;

/**
 * Created by tck
 * Date: 2017/12/29 11：06
 */

public class WorkHourDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvArrowLeftBlack;
    private TextView mTvTitle;
    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private ViewStub mViewStub;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_hour_detail;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
        }
    }
}
