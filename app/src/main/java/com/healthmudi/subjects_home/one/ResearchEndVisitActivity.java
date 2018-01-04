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
import com.healthmudi.subjects_home.one.fragment.ResearchEndVisitDetailFragment;
import com.healthmudi.subjects_home.one.fragment.ResearchEndVisitUpdateFragment;

/**
 * decription:研究结束访视
 * Created by tck on 2017/12/10.
 */

public class ResearchEndVisitActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvCheckMark;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    private ResearchEndVisitDetailFragment mResearchEndVisitDetailFragment;
    private ResearchEndVisitUpdateFragment mResearchEndVisitUpdateFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_research_end_visit;
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
        mTvTitle.setText("研究结束信息");
    }


    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsPersonalListBean != null) {
            mIvCheckMark.setVisibility(View.GONE);
            mResearchEndVisitDetailFragment = ResearchEndVisitDetailFragment.newInstance(mSubjectsBean,mSubjectsPersonalListBean);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, mResearchEndVisitDetailFragment, "ResearchEndVisitDetailFragment")
                    .commit();
        } else {
            mIvCheckMark.setVisibility(View.VISIBLE);
            mIvCheckMark.setOnClickListener(this);
            mResearchEndVisitUpdateFragment = ResearchEndVisitUpdateFragment.newInstance(mSubjectsBean);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, mResearchEndVisitUpdateFragment, "ResearchEndVisitUpdateFragment")
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
                finish();
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
                if (mResearchEndVisitUpdateFragment != null) {
                    mResearchEndVisitUpdateFragment.submitData();
                }
                break;

        }
    }


}
