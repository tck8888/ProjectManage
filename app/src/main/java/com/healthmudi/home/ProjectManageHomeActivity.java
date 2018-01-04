package com.healthmudi.home;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.entity.TabEntity;
import com.healthmudi.home.home_fragment.ProjectFragment;
import com.healthmudi.home.home_fragment.ScheduleFragment;

import java.util.ArrayList;

/**
 * 项目管理首页
 */
public class ProjectManageHomeActivity extends BaseActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private CommonTabLayout mCommonTablayout;

    private int[] mIconUnselectIds = {
            R.mipmap.icon_project_unselected, R.mipmap.icon_schedule_unselected};
    private int[] mIconSelectIds = {
            R.mipmap.icon_project_selected, R.mipmap.icon_schedule_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_manage_home;
    }

    @Override
    public void initData() {
        super.initData();
        mFragments.add(ProjectFragment.newInstance());
        mFragments.add(ScheduleFragment.newInstance());

        mTitles = getResources().getStringArray(R.array.project_manage_home_titles);
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
