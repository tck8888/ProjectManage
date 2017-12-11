package com.healthmudi.subjects_home.five;

import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;

/**
 * decription:机构立项
 * Created by tck on 2017/12/11.
 */

public class InstitutionEstablishmentActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_institution_establishment;
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.iv_check_mark:
                break;
        }
    }
}
