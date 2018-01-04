package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.bean.SubjectsListBean;

import java.util.List;

/**
 * decription:ProjectManage
 * Created by tck on 2017/12/9.
 */

public class SubjectsListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<SubjectsListBean> mDataList;
    private LayoutInflater mInflater;

    public SubjectsListAdapter(Context context, List<SubjectsListBean> subjectsListBeanList) {
        this.mContext = context;
        this.mDataList = subjectsListBeanList;
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
        return mDataList.get(groupPosition).getSubjects() == null ? 0 : mDataList.get(groupPosition).getSubjects().size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).getSubjects().get(childPosition);
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
        SubjectsListBean subjectsListBean = mDataList.get(groupPosition);
        mTvSubjectsGroupName.setText(subjectsListBean.getSite_name());
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
        convertView = mInflater.inflate(R.layout.subjects_child_item, parent, false);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        TextView mTvSubjectsPeopleName = (TextView) convertView.findViewById(R.id.tv_subjects_people_name);

        SubjectsListBean.SubjectsBean subjectsBean = mDataList.get(groupPosition).getSubjects().get(childPosition);
        mTvSubjectsPeopleName.setText(subjectsBean.getSubject_code() + "  (" + subjectsBean.getName_py() + ")");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.click(groupPosition, childPosition, "delete");
                }
            }
        });

        convertView.findViewById(R.id.into_subjecs_personal_fl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.click(groupPosition, childPosition, "");
                }
            }
        });
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnItemClick {
        void click(int groupPosition, int childPosition, String type);
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }
}
