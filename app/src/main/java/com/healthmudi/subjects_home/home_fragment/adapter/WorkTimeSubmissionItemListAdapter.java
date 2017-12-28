package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionItemListAdapter extends BasicAdapter<WorkTimeSubmissionItemListBean> {

    public WorkTimeSubmissionItemListAdapter(Context context, List<WorkTimeSubmissionItemListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvJobContent = get(convertView, R.id.tv_job_content);
        ImageView mIvIsChecked = get(convertView, R.id.iv_is_checked);
        View space = get(convertView, R.id.space);

        WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean = mDataList.get(position);

        mTvJobContent.setText(workTimeSubmissionItemListBean.getJob_type_name());
    }

    @Override
    protected int getContentView() {
        return R.layout.worktime_submission_item_list_item;
    }
}
