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
 * Date: 2018/01/05 10：08
 */

public class EDCFillInDetailFragment extends BaseFragment1 {


    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvOperationDate;
    private TextView mTvCrfPages;
    private TextView mTvJobTime;
    private TextView mTvRemark;

    private WorkingHoursListBean mWorkingHoursListBean;

    public static EDCFillInDetailFragment newInstance(WorkingHoursListBean workingHoursListBean){
        EDCFillInDetailFragment saeReportDetailFragment = new EDCFillInDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        saeReportDetailFragment.setArguments(bundle);
        return saeReportDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edc_fill_in_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvOperationDate = (TextView) view.findViewById(R.id.tv_operation_date);
        mTvCrfPages = (TextView) view.findViewById(R.id.tv_crf_pages);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mWorkingHoursListBean != null) {
            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            if (mWorkingHoursListBean.getOperation_date() != 0) {
                mTvOperationDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getKick_off_date()));
            }
            if (mWorkingHoursListBean.getCrf_pages() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getCrf_pages()));
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
