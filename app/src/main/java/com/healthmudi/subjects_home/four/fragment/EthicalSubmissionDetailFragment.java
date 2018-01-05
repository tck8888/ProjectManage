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

public class EthicalSubmissionDetailFragment extends BaseFragment1 {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvDocReceiveDate;
    private TextView mTvPiSubmitDate;
    private TextView mTvPiSignDate;
    private TextView mTvEcSubmitDate;
    private TextView mTvEcApproveDate;
    private TextView mTvDocumentsName;
    private AutoListView mAutoListView;
    private TextView mTvJobTime;
    private TextView mTvJobTime2;
    private TextView mTvRemark;

    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private ProgressListAdapter mAdapter;

    private WorkingHoursListBean mWorkingHoursListBean;

    public static EthicalSubmissionDetailFragment newInstance(WorkingHoursListBean workingHoursListBean){
        EthicalSubmissionDetailFragment contractFollowUpDetailFragment = new EthicalSubmissionDetailFragment();
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
            String[] strings2 = getResources().getStringArray(R.array.ec_submit_array);
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
        return R.layout.fragment_ethical_submission_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvDocReceiveDate = (TextView) view.findViewById(R.id.tv_doc_receive_date);
        mTvPiSubmitDate = (TextView) view.findViewById(R.id.tv_pi_submit_date);
        mTvPiSignDate = (TextView) view.findViewById(R.id.tv_pi_sign_date);
        mTvEcSubmitDate = (TextView) view.findViewById(R.id.tv_ec_submit_date);
        mTvEcApproveDate = (TextView) view.findViewById(R.id.tv_ec_approve_date);
        mTvDocumentsName = (TextView) view.findViewById(R.id.tv_documents_name);
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
            if (mWorkingHoursListBean.getPi_submit_date() != 0) {
                mTvPiSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getPi_submit_date()));
            }
            if (mWorkingHoursListBean.getPi_sign_date() != 0) {
                mTvPiSignDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getPi_sign_date()));
            }
            if (mWorkingHoursListBean.getEc_submit_date() != 0) {
                mTvEcSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getEc_submit_date()));
            }
            if (mWorkingHoursListBean.getEc_approve_date() != 0) {
                mTvEcApproveDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getEc_approve_date()));
            }
            if (!TextUtils.isEmpty(mWorkingHoursListBean.getDocuments_name())) {
                if (mWorkingHoursListBean.getDocuments_name().contains(",")) {
                    String documents_name = mWorkingHoursListBean.getDocuments_name();
                    mTvDocumentsName.setText(documents_name.replaceAll(",", "\\\n"));
                } else {
                    mTvDocumentsName.setText(mWorkingHoursListBean.getDocuments_name());
                }
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
