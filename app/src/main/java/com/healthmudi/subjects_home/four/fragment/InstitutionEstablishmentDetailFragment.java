package com.healthmudi.subjects_home.four.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.SiteApproveListBean;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.four.adapter.ProgressListAdapter;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/05 11：05
 */

public class InstitutionEstablishmentDetailFragment extends BaseFragment1 {


    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvSubmitDate;
    private TextView mTvApprovedDate;
    private AutoListView mAutoListView;
    private TextView mTvJobTime;
    private TextView mTvRemark;

    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private ProgressListAdapter mAdapter;
    private WorkingHoursListBean mWorkingHoursListBean;

    public static InstitutionEstablishmentDetailFragment newInstance(WorkingHoursListBean workingHoursListBean){
        InstitutionEstablishmentDetailFragment contractFollowUpDetailFragment = new InstitutionEstablishmentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        contractFollowUpDetailFragment.setArguments(bundle);
        return contractFollowUpDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
        if (mWorkingHoursListBean != null) {
            if (!ListUtil.isEmpty(mSiteApproveListBeen)) {
                mSiteApproveListBeen.clear();
            }
            String[] strings2 = getResources().getStringArray(R.array.site_approve_array);
            for (int i = 0; i < strings2.length; i++) {
                if (mWorkingHoursListBean.getStatus() == (i + 1)) {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], true));
                } else {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], false));
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_institution_establishment_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvSubmitDate = (TextView) view.findViewById(R.id.tv_submit_date);
        mTvApprovedDate = (TextView) view.findViewById(R.id.tv_approved_date);
        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new ProgressListAdapter(getContext(), mSiteApproveListBeen);
        mAutoListView.setAdapter(mAdapter);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mWorkingHoursListBean != null) {
            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            if (mWorkingHoursListBean.getSite_submit_date() != 0) {
                mTvSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_submit_date()));
            }

            if (mWorkingHoursListBean.getSite_approve_date() != 0) {
                mTvApprovedDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_approve_date()));
            }

            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getJob_time()));
            }

            if (!TextUtils.isEmpty(mWorkingHoursListBean.getRemark())) {
                mTvRemark.setText(mWorkingHoursListBean.getRemark());
            } else {
                mTvRemark.setVisibility(View.GONE);
            }
        }
    }
}
