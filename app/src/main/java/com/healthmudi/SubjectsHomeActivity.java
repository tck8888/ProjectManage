package com.healthmudi;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.entity.TabEntity;
import com.healthmudi.subjects_home.fragment.FileFragment;
import com.healthmudi.subjects_home.fragment.ReportFormFragment;
import com.healthmudi.subjects_home.fragment.SignFragment;
import com.healthmudi.subjects_home.fragment.SubjectsFragment;
import com.healthmudi.subjects_home.fragment.WorkingHoursFragment;

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
            R.mipmap.icon_sign_unselected, R.mipmap.icon_working_hours_unselected,
            R.mipmap.icon_report_form_unselected};
    private int[] mIconSelectIds = {
            R.mipmap.icon_subject_selected, R.mipmap.icon_file_selected,
            R.mipmap.icon_sign_selected, R.mipmap.icon_working_hours_selected,
            R.mipmap.icon_report_form_selected};

    private String[] mTitles;
    private String mProject_id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_subjects_home;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mProject_id = getIntent().getStringExtra(Constant.KEY_PROJECT_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mFragments.add(SubjectsFragment.newInstance(mProject_id));
        mFragments.add(FileFragment.newInstance(mProject_id));
        mFragments.add(SignFragment.newInstance(mProject_id));
        mFragments.add(WorkingHoursFragment.newInstance());
        mFragments.add(ReportFormFragment.newInstance());

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
    }
}
