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
import com.healthmudi.subjects_home.one.fragment.PlannedInterviewDetailFragment;
import com.healthmudi.subjects_home.one.fragment.PlannedInterviewUpdateFragment;

/**
 * decription:计划外访式
 * Created by tck on 2017/12/10.
 */
public class PlannedInterviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvCheckMark;

    private PlannedInterviewUpdateFragment mPlannedInterviewUpdateFragment;
    private PlannedInterviewDetailFragment mPlannedInterviewDetailFragment;
    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_interview;
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
        ImageView image = (ImageView) findViewById(R.id.iv_arrow_left_black);
        image.setImageResource(R.mipmap.arrow_left_black);
        mTvTitle.setText("计划外访视");
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsPersonalListBean == null) {
            mIvCheckMark.setVisibility(View.VISIBLE);
            mIvCheckMark.setOnClickListener(this);
            mPlannedInterviewUpdateFragment = PlannedInterviewUpdateFragment.newInstance(mSubjectsBean);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, mPlannedInterviewUpdateFragment, "PlannedInterviewUpdateFragment")
                    .commit();
        } else {
            mIvCheckMark.setVisibility(View.GONE);
            mPlannedInterviewDetailFragment = PlannedInterviewDetailFragment.newInstance(mSubjectsBean, mSubjectsPersonalListBean);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, mPlannedInterviewDetailFragment, "PlannedInterviewDetailFragment")
                    .commit();
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
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                intent.putExtra(Constant.KEY_INFOMATION, MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS);
                intent.putExtra(Constant.KEY_SUBJECT_ID, String.valueOf(mSubjectsBean.getSubject_id()));
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.iv_check_mark:
                if (mPlannedInterviewUpdateFragment != null) {
                    mPlannedInterviewUpdateFragment.submitData();
                }
                break;
        }
    }


}
