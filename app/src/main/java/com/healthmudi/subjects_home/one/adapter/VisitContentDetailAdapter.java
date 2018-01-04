package com.healthmudi.subjects_home.one.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.VisitContentBean;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/26 16：25
 */

public class VisitContentDetailAdapter extends BasicAdapter<VisitContentBean> {

    public VisitContentDetailAdapter(Context context, List<VisitContentBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        ImageView mIvParentName = get(convertView, R.id.iv_parent_name);
        TextView mTvParentName = get(convertView, R.id.tv_parent_name);
        TagFlowLayout mTagFlowlayout = get(convertView, R.id.tag_flowlayout);
        VisitContentBean visitContentBean = mDataList.get(position);
        mTvParentName.setText(visitContentBean.getCategory());
        if (visitContentBean.isSelected()) {
            mIvParentName.setImageResource(R.drawable.icon_rectangle_selected);
            mTvParentName.setTextColor(ContextCompat.getColor(mContext,R.color.color_1abc9c));
        } else {
            mIvParentName.setImageResource(R.drawable.icon_rectangle_unselected);
            mTvParentName.setTextColor(ContextCompat.getColor(mContext,R.color.color_464c5b));
        }
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(mContext, visitContentBean.getItems());
        mTagFlowlayout.setAdapter(categoryListAdapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.visit_content_item;
    }
}
