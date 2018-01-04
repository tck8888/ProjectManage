package com.healthmudi.subjects_home.one.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.ResearchEndReasonBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/22 15：49
 */

public class ResearchEndReasonListAdapter extends BasicAdapter<ResearchEndReasonBean> {

    public ResearchEndReasonListAdapter(Context context, List<ResearchEndReasonBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        ImageView mIvImageType = get(convertView, R.id.iv_image_type);
        TextView mTvReasonValue = get(convertView, R.id.tv_reason_value);
        ResearchEndReasonBean researchEndReasonBean = mDataList.get(position);
        mTvReasonValue.setText(researchEndReasonBean.getReason());
        if (researchEndReasonBean.isCheck()) {
            mIvImageType.setImageResource(R.mipmap.icon_work_manage_gx_press);
        } else {
            mIvImageType.setImageResource(R.mipmap.icon_work_manage_gx);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.item_research_end_reason;
    }
}
