package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
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
 * Date: 2017/12/29 13：10
 */

public class InstitutionEstablishmentDialog extends BaseDialogFragment {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvSubmitDate;
    private TextView mTvApprovedDate;
    private AutoListView mAutoListView;
    private TextView mTvWorkHour;
    private TextView mTvRemark;

    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private ProgressListAdapter mAdapter;
    private WorkingHoursListBean mWorkingHoursListBean;


    @Override
    protected void initData(@Nullable Bundle arguments) {
        mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
        if (mWorkingHoursListBean != null) {
            if (!ListUtil.isEmpty(mSiteApproveListBeen)) {
                mSiteApproveListBeen.clear();
            }
            String[] strings2 = getResources().getStringArray(R.array.site_approve_array);
            for (int i = 0; i < strings2.length; i++) {
                if (strings2[i].equals(mWorkingHoursListBean.getStatus())) {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], true));
                }else {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], false));
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.institution_establishment_detail;
    }

    @Override
    protected void initView(View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvSubmitDate = (TextView) view.findViewById(R.id.tv_submit_date);
        mTvApprovedDate = (TextView) view.findViewById(R.id.tv_approved_date);
        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new ProgressListAdapter(getContext(), mSiteApproveListBeen);
        mAutoListView.setAdapter(mAdapter);
        mTvWorkHour = (TextView) view.findViewById(R.id.tv_work_hour);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
        tvTitle.setText("机构立项");
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        if (mWorkingHoursListBean != null) {
            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            mTvSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_submit_date()));
            if (mWorkingHoursListBean.getSite_approve_date() != 0) {
                mTvApprovedDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getSite_approve_date()));
            }
            mTvWorkHour.setText(String.valueOf(mWorkingHoursListBean.getJob_time()));
            mTvRemark.setText(mWorkingHoursListBean.getRemark());
        }
    }
}
