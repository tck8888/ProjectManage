package com.healthmudi.subjects_home.five.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.ReportFormListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/18 10：00
 */

public class ReportFormListAdapter extends BasicAdapter<ReportFormListBean> {

    public ReportFormListAdapter(Context context, List<ReportFormListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {

        TextView mTvReportFormName = get(convertView, R.id.tv_report_form_name);
        TextView mTvReportFormProjectName = get(convertView, R.id.tv_report_form_project_name);
        TextView mTvReportFormCreateTime = get(convertView, R.id.tv_report_form_create_time);
        TextView mTvWorkSatus = get(convertView, R.id.tv_work_satus);

        ReportFormListBean reportFormListBean = mDataList.get(position);
        mTvReportFormName.setText(reportFormListBean.getReportFormName());
        mTvReportFormProjectName.setText(reportFormListBean.getProjectName());
        mTvReportFormCreateTime.setText(reportFormListBean.getDate());
        switch (reportFormListBean.getType()) {
            case "0":
                mTvWorkSatus.setText("已提交");
                mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));
                break;
            case "1":
                mTvWorkSatus.setText("未提交");
                mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
                break;
        }


    }

    @Override
    protected int getContentView() {
        return R.layout.report_form_list_item;
    }
}
