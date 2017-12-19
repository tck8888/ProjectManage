package com.healthmudi.subjects_home.five.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.ReportFormDetailBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/19 13：09
 */

public class SiteSituationListAdapter extends BasicAdapter<ReportFormDetailBean> {

    public SiteSituationListAdapter(Context context, List<ReportFormDetailBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvIndexName = get(convertView, R.id.tv_index_name);
        TextView mTvIndexCount = get(convertView, R.id.tv_index_count);
        ReportFormDetailBean reportFormDetailBean = mDataList.get(position);
        mTvIndexName.setText(reportFormDetailBean.getIndex());
        mTvIndexCount.setText(reportFormDetailBean.getResult());
    }

    @Override
    protected int getContentView() {
        return R.layout.site_situation_list_item;
    }
}
