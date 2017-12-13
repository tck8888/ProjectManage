package com.healthmudi.subjects_home.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.WorkingHoursListBean;

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
        WorkingHoursListBean workingHoursListBean = mDataList.get(position);
        if (workingHoursListBean.status==0){
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
        }else {
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_2bc8a0));
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.working_hours_list_item;
    }
}
