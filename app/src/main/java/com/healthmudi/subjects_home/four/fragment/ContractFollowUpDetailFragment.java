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

public class ContractFollowUpDetailFragment extends BaseFragment1 {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvDocReceiveDate;
    private TextView mTvSiteSubmitDate;
    private TextView mTvSiteApproveDate;
    private TextView mTvSiteSignDate;
    private TextView mTvSponsorSignDate;
    private AutoListView mAutoListView;
    private TextView mTvJobTime;
    private TextView mTvJobTime2;
    private TextView mTvRemark;

    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private ProgressListAdapter mAdapter;

    private WorkingHoursListBean mWorkingHoursListBean;

    public static ContractFollowUpDetailFragment newInstance(WorkingHoursListBean workingHoursListBean) {
        ContractFollowUpDetailFragment contractFollowUpDetailFragment = new ContractFollowUpDetailFragment();
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
            String[] strings2 = getResources().getStringArray(R.array.cont_follow_array);
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
        return R.layout.fragment_contract_follow_up_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvDocReceiveDate = (TextView) view.findViewById(R.id.tv_doc_receive_date);
        mTvSiteSubmitDate = (TextView) view.findViewById(R.id.tv_site_submit_date);
        mTvSiteApproveDate = (TextView) view.findViewById(R.id.tv_site_approve_date);
        mTvSiteSignDate = (TextView) view.findViewById(R.id.tv_site_sign_date);
        mTvSponsorSignDate = (TextView) view.findViewById(R.id.tv_sponsor_sign_date);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvJobTime2 = (TextView) view.findViewById(R.id.tv_job_time2);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);

        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new ProgressListAdapter(getContext(), mSiteApproveListBeen);
        mAutoListView.setAdapter(mAdapter);

    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mWorkingHoursListBean != null) {

            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());

            if (mWorkingHoursListBean.getDoc_receive_date() != 0) {
                mTvDocReceiveDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getDoc_receive_date()));
            }
            if (mWorkingHoursListBean.getSite_submit_date() != 0) {
                mTvSiteSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_submit_date()));
            }
            if (mWorkingHoursListBean.getSite_approve_date() != 0) {
                mTvSiteApproveDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_approve_date()));
            }
            if (mWorkingHoursListBean.getSite_sign_date() != 0) {
                mTvSiteSignDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_sign_date()));
            }
            if (mWorkingHoursListBean.getSponsor_sign_date() != 0) {
                mTvSponsorSignDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSponsor_sign_date()));
            }

            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getJob_time()));
            }
            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime2.setText(String.valueOf(mWorkingHoursListBean.getJob_time2()));
            }
            if (!TextUtils.isEmpty(mWorkingHoursListBean.getRemark())) {
                mTvRemark.setText(mWorkingHoursListBean.getRemark());
            } else {
                mTvRemark.setVisibility(View.GONE);
            }
        }
    }
}
