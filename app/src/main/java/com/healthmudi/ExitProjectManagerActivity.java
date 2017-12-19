package com.healthmudi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.base.BaseActivity;

/**
 * decription:退出项目管理系统
 * Created by tck on 2017/12/19.
 */

public class ExitProjectManagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvDefaultHeadPortrait;
    private TextView mTvUserNickname;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exit_project_manager;
    }

    @Override
    public void initView() {
        super.initView();

        mIvDefaultHeadPortrait = (ImageView) findViewById(R.id.iv_default_head_portrait);
        mTvUserNickname = (TextView) findViewById(R.id.tv_user_nickname);

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.tv_exit_app).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.tv_exit_app:
                break;
        }
    }
}
