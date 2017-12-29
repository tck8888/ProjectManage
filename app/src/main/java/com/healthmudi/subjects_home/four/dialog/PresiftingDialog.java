package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.utils.DateUtils;


/**
 * Created by tck
 * Date: 2017/12/29 13：10
 */

public class PresiftingDialog extends BaseDialogFragment {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvOperationDate;
    private TextView mTvPrescreenCount;
    private TextView mTvMeetCount;
    private TextView mTvJobTime;
    private TextView mTvRemark;

    private WorkingHoursListBean mWorkingHoursListBean;


    @Override
    protected void initData(@Nullable Bundle arguments) {
        mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_presifting;
    }

    @Override
    protected void initView(View view) {

        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvOperationDate = (TextView) view.findViewById(R.id.tv_operation_date);
        mTvPrescreenCount = (TextView) view.findViewById(R.id.tv_prescreen_count);
        mTvMeetCount = (TextView) view.findViewById(R.id.tv_meet_count);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);

    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        if (mWorkingHoursListBean != null) {
            tvTitle.setText(mWorkingHoursListBean.getJob_type_name());
            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            if (mWorkingHoursListBean.getOperation_date() != 0) {
                mTvOperationDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getOperation_date()));
            }
            if (mWorkingHoursListBean.getPrescreen_count() != 0) {
                mTvPrescreenCount.setText(String.valueOf(mWorkingHoursListBean.getPrescreen_count()));
            }
            if (mWorkingHoursListBean.getMeet_count() != 0) {
                mTvMeetCount.setText(String.valueOf(mWorkingHoursListBean.getMeet_count()));
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
