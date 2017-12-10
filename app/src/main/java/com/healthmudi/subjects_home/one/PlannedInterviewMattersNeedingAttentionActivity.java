package com.healthmudi.subjects_home.one;

import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;

/**
 * decription:注意事项
 * Created by tck on 2017/12/10.
 */

public class PlannedInterviewMattersNeedingAttentionActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvPlannedInterviewMattersNeedingAttention;

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_interview_matters_needing_attention;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        mTvPlannedInterviewMattersNeedingAttention = (TextView) findViewById(R.id.tv_planned_interview_matters_needing_attention);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mTvPlannedInterviewMattersNeedingAttention = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
