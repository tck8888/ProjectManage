package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.SubjectsPersonalListBean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class SubjectsPersonalListAdapter extends BasicAdapter<SubjectsPersonalListBean> {

    public SubjectsPersonalListAdapter(Context context, List<SubjectsPersonalListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        ImageView mIvTypeIcon = get(convertView, R.id.iv_type_icon);
        TextView mTvItemName = get(convertView, R.id.tv_item_name);
        TextView mTvItemValue = get(convertView, R.id.tv_item_value);
        TextView mTvItemStatus = get(convertView, R.id.tv_item_status);

        SubjectsPersonalListBean subjectsPersonalListBean = mDataList.get(position);
        if (subjectsPersonalListBean.type.equals("top")) {
            mIvTypeIcon.setImageResource(R.mipmap.icon_in_the_group);
            if (subjectsPersonalListBean.status == 1) {
                mTvItemName.setText("入组");
                mTvItemStatus.setText("已入组");
                mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_3398db));
            }
        } else if (subjectsPersonalListBean.type.equals("center")) {
            mIvTypeIcon.setImageResource(R.mipmap.icon_cycle);
            if (subjectsPersonalListBean.status == 1) {
                mTvItemName.setText(subjectsPersonalListBean.name);
                mTvItemStatus.setText("已完成");
                mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_2bc8a0));
            }
        } else {
            mIvTypeIcon.setImageResource(R.mipmap.icon_research_end);
            if (subjectsPersonalListBean.status == 0) {
                mTvItemName.setText("研究结束");
                mTvItemStatus.setText("已退出");
                mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_f04844));
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.subjects_personal_list_item;
    }
}
