package com.healthmudi.subjects_home.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.utils.DateUtils;

/**
 * Created by tck
 * Date: 2018/01/04 18：21
 */

public class PlannedInterviewDetailFragment extends BaseFragment1 {


    private TextView mTvSelectResearchCenter;
    private TextView mTvInputNumber;
    private TextView mTvInitials;
    private TextView mTvVisitDate;
    private TextView mTvRemark;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;


    public static PlannedInterviewDetailFragment newInstance(SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        PlannedInterviewDetailFragment plannedInterviewDetailFragment = new PlannedInterviewDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        plannedInterviewDetailFragment.setArguments(bundle);
        return plannedInterviewDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.getSerializable(Constant.KEY_SUBJECTS_BEAN);
        mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_planned_interview_detail;
    }

    @Override
    protected void initView(@Nullable View view) {

        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mTvInputNumber = (TextView) view.findViewById(R.id.tv_input_number);
        mTvInitials = (TextView) view.findViewById(R.id.tv_initials);
        mTvVisitDate = (TextView) view.findViewById(R.id.tv_visit_date);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);

    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null) {
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInputNumber.setText(mSubjectsBean.getSubject_code());
            mTvInitials.setText(mSubjectsBean.getName_py());
        }

        if (mSubjectsPersonalListBean != null) {
            mTvVisitDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsPersonalListBean.getActual_visit_time())));
            if (TextUtils.isEmpty(mSubjectsPersonalListBean.getRemark())) {
                mTvRemark.setVisibility(View.GONE);
            } else {
                mTvRemark.setText(mSubjectsPersonalListBean.getRemark());
            }
        }
    }
}
