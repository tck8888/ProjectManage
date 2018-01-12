package com.healthmudi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.home.ProjectManageHomeActivity;
import com.orhanobut.hawk.Hawk;

/**
 * Created by tck
 * Date: 2018/01/09 15：57
 */

public class TestActivity extends BaseActivity {

    private EditText mEtToken;
    private RadioGroup mRgEnv;
    private RadioButton mRbTest;
    private RadioButton mRbDev;

    private static final int BAIDU_READ_PHONE_STATE = 100;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        super.initView();

        Hawk.put(Constant.KEY_PRODUCTION_BASE_URL, ProjectManageHttpUrlList.BASE_URL);

        mRgEnv = (RadioGroup) findViewById(R.id.rg_env);
        mRbTest = (RadioButton) findViewById(R.id.rb_test);
        mRbTest.setChecked(true);
        Hawk.put(Constant.KEY_BASE_URL, ProjectManageHttpUrlList.TEST_BASE_URL);
        mRbDev = (RadioButton) findViewById(R.id.rb_dev);
        mRbTest.setText("测试环境  " + ProjectManageHttpUrlList.TEST_BASE_URL);
        mRbDev.setText("正式环境  " + ProjectManageHttpUrlList.PRODUCTION_BASE_URL);

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

        mRgEnv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_test) {
                    Hawk.put(Constant.KEY_BASE_URL, ProjectManageHttpUrlList.TEST_BASE_URL);
                } else {
                    Hawk.put(Constant.KEY_BASE_URL, ProjectManageHttpUrlList.PRODUCTION_BASE_URL);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
            }
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获取到权限，做相应处理
                    //调用定位SDK应确保相关权限均被授权，否则会引起定位失败
                } else {
                    //没有获取到权限，做特殊处理
                }
                break;
            default:
                break;
        }
    }
}
