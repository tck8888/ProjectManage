package com.healthmudi.subjects_home.four.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.utils.DateUtils;

/**
 * Created by tck
 * Date: 2018/01/05 11：05
 */

public class ProjectStartMeetingDetailFragment extends BaseFragment1 {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvKickOffDate;
    private TextView mTvJobTime;
    private TextView mTvRemark;

    private WorkingHoursListBean mWorkingHoursListBean;

    public static ProjectStartMeetingDetailFragment newInstance(WorkingHoursListBean workingHoursListBean){
        ProjectStartMeetingDetailFragment contractFollowUpDetailFragment = new ProjectStartMeetingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        contractFollowUpDetailFragment.setArguments(bundle);
        return contractFollowUpDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_start_meeting_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvKickOffDate = (TextView) view.findViewById(R.id.tv_kick_off_date);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mWorkingHoursListBean != null) {

            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            if (mWorkingHoursListBean.getKick_off_date() != 0) {
                mTvKickOffDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getKick_off_date()));
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
