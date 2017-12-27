package com.healthmudi.subjects_home;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.entity.TabEntity;
import com.healthmudi.subjects_home.home_fragment.FileFragment;
import com.healthmudi.subjects_home.home_fragment.SignFragment;
import com.healthmudi.subjects_home.home_fragment.SubjectsFragment;
import com.healthmudi.subjects_home.home_fragment.WorkingHoursFragment;

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
        mFragments.add(SignFragment.newInstance(String.valueOf(mProjectListBean.getProject_id())));
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
    }
}
