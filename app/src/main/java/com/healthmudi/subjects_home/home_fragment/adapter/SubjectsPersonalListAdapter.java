package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;

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
        mTvItemName.setText(subjectsPersonalListBean.getVisit_name());

        if (subjectsPersonalListBean.getVisit_type() == 1) {//入组
            mIvTypeIcon.setImageResource(R.mipmap.icon_in_the_group);
            mTvItemStatus.setText("已入组");
            mTvItemStatus.setVisibility(View.VISIBLE);
            mTvItemValue.setText(DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getTarget_visit_time())));
            mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_3398db));
        } else if (subjectsPersonalListBean.getVisit_type() == 3) {//退出访视
            mIvTypeIcon.setImageResource(R.mipmap.icon_research_end);
            mTvItemStatus.setText("已退出");
            mTvItemStatus.setVisibility(View.VISIBLE);
            mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_f04844));
            mTvItemValue.setText(DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getTarget_visit_time())));
        } else if (subjectsPersonalListBean.getVisit_type() == 4) {//计划外访试
            mIvTypeIcon.setImageResource(R.mipmap.icon_cycle);
            mTvItemStatus.setText("已完成");
            mTvItemStatus.setVisibility(View.VISIBLE);
            mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));
            mTvItemValue.setText(DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getActual_visit_time())));
        } else {//常规
            mIvTypeIcon.setImageResource(R.mipmap.icon_cycle);
            if (subjectsPersonalListBean.getNot_finish_flag() == 1) {
                mTvItemStatus.setText("未完成");
                mTvItemStatus.setVisibility(View.VISIBLE);
                mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
                mTvItemValue.setText(DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getTarget_visit_time())));
            } else if (subjectsPersonalListBean.getActual_visit_time() != 0 && subjectsPersonalListBean.getNot_finish_flag() == 0) {
                mTvItemStatus.setText("已完成");
                mTvItemStatus.setVisibility(View.VISIBLE);
                mTvItemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));

                int differenceDay = DateUtils.getDifferenceDay(subjectsPersonalListBean.getTarget_visit_time(), subjectsPersonalListBean.getActual_visit_time());
                String formatTime = DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getTarget_visit_time()));
                int color = 0;
                if ((differenceDay < subjectsPersonalListBean.getWindow_neg())//窗口期外显示红色
                        || (differenceDay > subjectsPersonalListBean.getWindow_pos())) {
                    color = ContextCompat.getColor(mContext, R.color.color_f04844);
                } else {
                    color = ContextCompat.getColor(mContext, R.color.color_657180);
                }
                mTvItemValue.setText(CommonUtils.getSpannableString(formatTime + " ( " + differenceDay + " )", color));
            } else {
                mTvItemStatus.setVisibility(View.INVISIBLE);
                mTvItemValue.setText(DateUtils.getFormatTime(String.valueOf(subjectsPersonalListBean.getTarget_visit_time())));
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.subjects_personal_list_item;
    }
}
