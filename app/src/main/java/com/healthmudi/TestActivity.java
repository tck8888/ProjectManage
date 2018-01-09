package com.healthmudi;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.home.ProjectManageHomeActivity;
import com.orhanobut.hawk.Hawk;

/**
 * Created by tck
 * Date: 2018/01/09 15：57
 */

public class TestActivity extends BaseActivity {

    private EditText mEtToken;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        super.initView();

        mEtToken = (EditText) findViewById(R.id.et_token);
        findViewById(R.id.btn_login_project_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        String token = (String) Hawk.get(Constant.KEY_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            mEtToken.setText(token);
        }
    }


    private void login() {
        String token = mEtToken.getText().toString().trim();

        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this, "请填写测试ID", Toast.LENGTH_SHORT).show();
            return;
        }
        Hawk.put(Constant.KEY_TOKEN, token);
        Intent intent = new Intent(TestActivity.this, ProjectManageHomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }
}
