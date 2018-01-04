package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.subjects_home.one.fragment.RegularVisitsDetailFragment;
import com.healthmudi.subjects_home.one.fragment.RegularVisitsUpdateFragment;

/**
 * decription:常规访视
 * Created by tck on 2017/12/10.
 */

public class RegularVisitsActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvCheckMark;

    private RegularVisitsDetailFragment mRegularVisitsDetailFragment;
    private RegularVisitsUpdateFragment mRegularVisitsUpdateFragment;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regular_visits;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mSubjectsBean = (SubjectsListBean.SubjectsBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_BEAN);
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvCheckMark = (ImageView) findViewById(R.id.iv_check_mark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null && mSubjectsPersonalListBean != null) {
            mTvTitle.setText(mSubjectsPersonalListBean.getVisit_name());
            //访视类型 1.入组访视、2.常规访视、3.退出访视、4.计划外访视
            if (mSubjectsPersonalListBean.getVisit_type() == 2
                    && mSubjectsPersonalListBean.getActual_visit_time() != 0) {
                //看详情
                mIvCheckMark.setVisibility(View.GONE);
                mRegularVisitsDetailFragment = RegularVisitsDetailFragment.newInstance(mSubjectsBean, mSubjectsPersonalListBean);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_container, mRegularVisitsDetailFragment, "RegularVisitsDetailFragment")
                        .commit();

            } else {
                mIvCheckMark.setVisibility(View.VISIBLE);
                mIvCheckMark.setOnClickListener(this);
                mRegularVisitsUpdateFragment = RegularVisitsUpdateFragment.newInstance(mSubjectsBean, mSubjectsPersonalListBean);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_container, mRegularVisitsUpdateFragment, "RegularVisitsUpdateFragment")
                        .commit();
            }
        }
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_circular_exclamation_mark://注意事项
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                intent.putExtra(Constant.KEY_INFOMATION, MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS);
                intent.putExtra(Constant.KEY_SUBJECT_ID, String.valueOf(mSubjectsBean.getSubject_id()));
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.iv_check_mark:
                if (mRegularVisitsUpdateFragment != null) {
                    mRegularVisitsUpdateFragment.submitData();
                }
                break;
        }
    }

}
