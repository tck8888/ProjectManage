package com.healthmudi.subjects_home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.entity.TabEntity;
import com.healthmudi.subjects_home.home_fragment.FileFragment;
import com.healthmudi.subjects_home.home_fragment.SignFragment;
import com.healthmudi.subjects_home.home_fragment.SubjectsFragment;
import com.healthmudi.subjects_home.home_fragment.WorkingHoursFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * decription:受试者主页
 * Created by tck on 2017/12/9.
 */

public class SubjectsHomeActivity extends BaseActivity {

    private CommonTabLayout mCommonTablayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.icon_subject_unselected, R.mipmap.icon_file_unselected,
            R.mipmap.icon_sign_unselected, R.mipmap.icon_working_hours_unselected};
    private int[] mIconSelectIds = {
            R.mipmap.icon_subject_selected, R.mipmap.icon_file_selected,
            R.mipmap.icon_sign_selected, R.mipmap.icon_working_hours_selected
    };

    private String[] mTitles;
    private ProjectListBean mProjectListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_subjects_home;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mProjectListBean = (ProjectListBean) getIntent().getSerializableExtra(Constant.KEY_PROJECT_LIST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mFragments.add(SubjectsFragment.newInstance(mProjectListBean));
        mFragments.add(FileFragment.newInstance(String.valueOf(mProjectListBean.getProject_id())));
        mFragments.add(SignFragment.newInstance(mProjectListBean));
        mFragments.add(WorkingHoursFragment.newInstance(String.valueOf(mProjectListBean.getProject_id())));

        mTitles = getResources().getStringArray(R.array.subject_home_titles);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    @Override
    public void initView() {
        super.initView();
        mCommonTablayout = (CommonTabLayout) findViewById(R.id.common_tablayout);
        mCommonTablayout.setTabData(mTabEntities, this, R.id.container_fl, mFragments);

        //检查版本是否大于M,权限校验
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        Constant.PERMISSION_READ_LOCATION_STATE);
            } else {//权限通过
                EventBus.getDefault().post(new MessageEvent<Boolean>(MessageEvent.KEY_LOCATION_SETTINGS, true));
            }
        } else {//不用验证权限
            EventBus.getDefault().post(new MessageEvent<Boolean>(MessageEvent.KEY_LOCATION_SETTINGS, true));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RQ_ACTION_LOCATION_SETTINGS) {
            //说明是从定位设置过来了， 发送广播通知，
            EventBus.getDefault().post(new MessageEvent<Boolean>(MessageEvent.KEY_LOCATION_SETTINGS,true));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.PERMISSION_READ_LOCATION_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            EventBus.getDefault().post(new MessageEvent<Boolean>(MessageEvent.KEY_LOCATION_SETTINGS, true));
        } else {
            Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent<Boolean>(MessageEvent.KEY_LOCATION_SETTINGS, false));
        }
    }


}
