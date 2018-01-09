package com.healthmudi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.home.ProjectManageHomeActivity;
import com.healthmudi.service.LocationService;
import com.orhanobut.hawk.Hawk;

/**
 * Created by tck
 * Date: 2018/01/09 15：57
 */

public class TestActivity extends BaseActivity {

    private EditText mEtToken;
    private LocationService locationService;
    private static final int BAIDU_READ_PHONE_STATE = 100;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((ProjectApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);

        locationService.start();
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
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

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                System.out.println("latitude====" +latitude);
                System.out.println("longitude====" +longitude);

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    System.out.println("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    System.out.println("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    System.out.println("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }

            }
        }

    };
}
