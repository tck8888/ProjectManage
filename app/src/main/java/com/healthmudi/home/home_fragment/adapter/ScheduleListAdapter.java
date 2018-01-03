package com.healthmudi.home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.bean.ItemType;
import com.healthmudi.bean.MemoBean;
import com.healthmudi.bean.ScheduleListBean;
import com.healthmudi.bean.ScheduleListFootBean;
import com.healthmudi.bean.SubjectsBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 10：17
 */

public class ScheduleListAdapter extends BaseExpandableListAdapter {


    private Context mContext;
    private List<ItemType> mDataList;
    private LayoutInflater mInflater;

    public ScheduleListAdapter(Context context, List<ItemType> scheduleListBeen) {
        this.mContext = context;
        this.mDataList = scheduleListBeen;
        mInflater = LayoutInflater.from(context);
    }


    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataList.get(groupPosition).isVisit()) {
            ScheduleListBean.VisitBean visitBean = (ScheduleListBean.VisitBean) mDataList.get(groupPosition);
            return visitBean.getSubjects() == null ? 0 : visitBean.getSubjects().size();
        }
        ScheduleListFootBean scheduleListFootBean = (ScheduleListFootBean) mDataList.get(groupPosition);
        return scheduleListFootBean.getMemo() == null ? 0 : scheduleListFootBean.getMemo().size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mDataList.get(groupPosition).isVisit()) {
            ScheduleListBean.VisitBean visitBean = (ScheduleListBean.VisitBean) mDataList.get(groupPosition);
            return visitBean.getSubjects().get(childPosition);
        }
        ScheduleListFootBean scheduleListFootBean = (ScheduleListFootBean) mDataList.get(groupPosition);
        return scheduleListFootBean.getMemo().get(childPosition);

    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.subjects_group_item, parent, false);
        ImageView mIvAddSubjects = (ImageView) convertView.findViewById(R.id.iv_add_subjects);
        TextView mTvSubjectsGroupName = (TextView) convertView.findViewById(R.id.tv_subjects_group_name);

        if (mDataList.get(groupPosition).isVisit()) {
            ScheduleListBean.VisitBean scheduleListBean = (ScheduleListBean.VisitBean) mDataList.get(groupPosition);
            mTvSubjectsGroupName.setText(scheduleListBean.getProject_name());
        } else {
            ScheduleListFootBean scheduleListFootBean = (ScheduleListFootBean) mDataList.get(groupPosition);
            mTvSubjectsGroupName.setText(scheduleListFootBean.getProject_name());
        }

        if (isExpanded) {
            mIvAddSubjects.setImageResource(R.mipmap.icon_circular_down);
        } else {
            mIvAddSubjects.setImageResource(R.mipmap.icon_circular_up);
        }
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_memo_list, parent, false);


        TextView mTvWorkSatus = (TextView) convertView.findViewById(R.id.tv_work_satus);
        ImageView mIvImageType = (ImageView) convertView.findViewById(R.id.iv_image_type);
        TextView mTvSubjectsPeopleName = (TextView) convertView.findViewById(R.id.tv_subjects_people_name);

        if (mDataList.get(groupPosition).isVisit()) {
            ScheduleListBean.VisitBean scheduleListBean = (ScheduleListBean.VisitBean) mDataList.get(groupPosition);
            SubjectsBean subjectsBean = scheduleListBean.getSubjects().get(childPosition);
            mTvSubjectsPeopleName.setText(subjectsBean.getSubject_code() + "  (" + subjectsBean.getName_py() + ")");
            mIvImageType.setImageResource(R.mipmap.icon_subjects_people);

        } else {
            ScheduleListFootBean scheduleListFootBean = (ScheduleListFootBean) mDataList.get(groupPosition);
            MemoBean memoBean = scheduleListFootBean.getMemo().get(childPosition);
            mTvSubjectsPeopleName.setText(memoBean.getMemo_content());
            mIvImageType.setImageResource(R.mipmap.icon_memo_black);

            if (memoBean.getStatus() == 0) {
                mTvWorkSatus.setText("未完成");
                mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
            } else {
                mTvWorkSatus.setText("已完成");
                mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));
            }
        }
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}