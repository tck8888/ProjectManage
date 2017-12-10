package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;

/**
 * decription:计划外访式
 * Created by tck on 2017/12/10.
 */
public class PlannedInterviewActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_interview;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
