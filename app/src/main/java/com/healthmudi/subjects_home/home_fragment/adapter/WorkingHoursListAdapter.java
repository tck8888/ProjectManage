package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.StringConvertCodeEachUtils;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class WorkingHoursListAdapter extends BasicAdapter<WorkingHoursListBean> {

    public WorkingHoursListAdapter(Context context, List<WorkingHoursListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {

        TextView mTvWorkTime = get(convertView, R.id.tv_work_time);
        TextView mTvWorkContent = get(convertView, R.id.tv_work_content);
        TextView mTvSubjectsCause = get(convertView, R.id.tv_subjects_cause);
        TextView mTvUesedTime = get(convertView, R.id.tv_uesed_time);
        TextView mTvWorkSatus = get(convertView, R.id.tv_work_satus);
        TextView mTvCount = get(convertView, R.id.tv_count);

        WorkingHoursListBean workingHoursListBean = mDataList.get(position);

        mTvWorkTime.setText(DateUtils.getFormatTime2(workingHoursListBean.getCreate_time()));
        mTvWorkContent.setText(workingHoursListBean.getJob_type_name());

        String progress = "";
        if (workingHoursListBean.getJob_type_id() == 1) {
            progress = StringConvertCodeEachUtils.getInstitutionEstablishmentStr(workingHoursListBean.getStatus());
        } else if (workingHoursListBean.getJob_type_id() == 2) {
            progress = StringConvertCodeEachUtils.getEthicalSubmission(workingHoursListBean.getStatus());
        } else if (workingHoursListBean.getJob_type_id() == 3) {
            progress = StringConvertCodeEachUtils.getContractFollowUp(workingHoursListBean.getStatus());
        } else {
            progress = workingHoursListBean.getJob_type_name();
        }
        if (workingHoursListBean.getJob_type_id() == 6) {
            mTvCount.setVisibility(View.VISIBLE);
            mTvCount.setText("数量:  " + workingHoursListBean.getPrescreen_count());
        } else if (workingHoursListBean.getJob_type_id() == 8) {
            mTvCount.setVisibility(View.VISIBLE);
            mTvCount.setText("数量:  " + workingHoursListBean.getCrf_pages());
        } else if (workingHoursListBean.getJob_type_id() == 99) {
            mTvCount.setVisibility(View.VISIBLE);
            mTvCount.setText("数量:  " + workingHoursListBean.getJob_count());
        }else if (workingHoursListBean.getJob_type_id() == 9) {
            mTvCount.setVisibility(View.VISIBLE);
            mTvCount.setText("数量:  " + workingHoursListBean.getJob_count());
        }else {
            mTvCount.setVisibility(View.INVISIBLE);
        }
        String status = StringConvertCodeEachUtils.getWorkConetntStatus(progress);
        mTvUesedTime.setText("用时:  " + (workingHoursListBean.getJob_time() + workingHoursListBean.getJob_time2()) + "小时");
        mTvSubjectsCause.setText(progress);

        mTvWorkSatus.setText(status);

        if (status.equals("未完成")) {
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
        } else {
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.item_working_hours_list;
    }
}
