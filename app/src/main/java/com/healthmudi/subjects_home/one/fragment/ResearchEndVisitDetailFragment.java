package com.healthmudi.subjects_home.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.ResearchEndReasonBean;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.one.adapter.ResearchEndReasonListAdapter;
import com.healthmudi.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/04 19：06
 */

public class ResearchEndVisitDetailFragment extends BaseFragment1 {

    private TextView mTvSelectResearchCenter;
    private TextView mTvInputNumber;
    private TextView mTvInitials;
    private AutoListView mAutoListView;
    private TextView mTvOtherReason;
    private TextView mTvEndTime;
    private TextView mTvRemark;

    private ResearchEndReasonListAdapter mAdapter;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;
    private List<ResearchEndReasonBean> mResearchEndReasonBeanList = new ArrayList<>();

    public static ResearchEndVisitDetailFragment newInstance(SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        ResearchEndVisitDetailFragment researchEndVisitDetailFragment = new ResearchEndVisitDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        researchEndVisitDetailFragment.setArguments(bundle);
        return researchEndVisitDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {
            String[] stringArray = getResources().getStringArray(R.array.research_end_reason);
            for (int i = 0; i < stringArray.length; i++) {
                mResearchEndReasonBeanList.add(new ResearchEndReasonBean(i + 1, stringArray[i], false));
            }
            mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.getSerializable(Constant.KEY_SUBJECTS_BEAN);
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_research_end_visit_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mTvInputNumber = (TextView) view.findViewById(R.id.tv_input_number);
        mTvInitials = (TextView) view.findViewById(R.id.tv_initials);
        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new ResearchEndReasonListAdapter(getContext(), mResearchEndReasonBeanList);
        mAutoListView.setAdapter(mAdapter);
        mTvOtherReason = (TextView) view.findViewById(R.id.tv_other_reason);
        mTvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null) {
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInputNumber.setText(mSubjectsBean.getSubject_code());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvOtherReason.setText(mSubjectsBean.getEnd_reason_description());
            mTvEndTime.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsBean.getEnd_date())));
            updateEndReason(mSubjectsBean);
        }

        if (mSubjectsPersonalListBean != null) {
            if (TextUtils.isEmpty(mSubjectsPersonalListBean.getRemark())) {
                mTvRemark.setVisibility(View.GONE);
            } else {
                mTvRemark.setText(mSubjectsPersonalListBean.getRemark());
            }
        }
    }

    private void updateEndReason(SubjectsListBean.SubjectsBean subjectsBean) {
        int end_reason = subjectsBean.getEnd_reason();
        for (ResearchEndReasonBean researchEndReasonBean : mResearchEndReasonBeanList) {
            if (researchEndReasonBean.getId() == end_reason) {
                researchEndReasonBean.setCheck(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
