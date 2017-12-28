package com.healthmudi.home.home_fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.ProjectListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/11/29 13：06
 */

public class ProjectListAdapter extends BasicAdapter<ProjectListBean> {

    public ProjectListAdapter(Context context, List<ProjectListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvItemValue = get(convertView, R.id.tv_item_value);
        View mItemDivider = get(convertView, R.id.item_divider);
        ProjectListBean projectListBean = mDataList.get(position);
        if (projectListBean != null) {
            String project_name = projectListBean.getProject_name();
            mTvItemValue.setText(project_name);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.project_list_item;
    }

}
