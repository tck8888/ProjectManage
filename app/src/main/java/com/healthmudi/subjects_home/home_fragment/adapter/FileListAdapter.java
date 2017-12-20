package com.healthmudi.subjects_home.home_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.bean.FileListBean;

import java.util.List;

/**
 * decription:ProjectManage
 * Created by tck on 2017/12/9.
 */

public class FileListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<FileListBean> mDataList;
    private LayoutInflater mInflater;

    public FileListAdapter(Context context, List<FileListBean> subjectsListBeanList) {
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
        return mDataList.get(groupPosition) == null ? 0 : mDataList.get(groupPosition).getFiles().size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).getFiles().get(childPosition);
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

        ImageView mIvImageType = (ImageView) convertView.findViewById(R.id.iv_image_type);
        mIvImageType.setImageResource(R.mipmap.icon_file_folder);
        ImageView mIvAddSubjects = (ImageView) convertView.findViewById(R.id.iv_add_subjects);
        TextView mTvSubjectsGroupName = (TextView) convertView.findViewById(R.id.tv_subjects_group_name);
        FileListBean fileListBean = mDataList.get(groupPosition);
        mTvSubjectsGroupName.setText(fileListBean.getFolder());
        if (isExpanded) {
            mIvAddSubjects.setImageResource(R.mipmap.icon_circular_down);
        } else {
            mIvAddSubjects.setImageResource(R.mipmap.icon_circular_up);
        }
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.file_child_item, parent, false);

        TextView mTvSubjectsPeopleName = (TextView) convertView.findViewById(R.id.tv_subjects_people_name);
        FileListBean.FilesBean filesBean = mDataList.get(groupPosition).getFiles().get(childPosition);
        mTvSubjectsPeopleName.setText(filesBean.getFile_name());
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
