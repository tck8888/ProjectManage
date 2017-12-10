package com.healthmudi.subjects_home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.FileListBean;
import com.healthmudi.subjects_home.adapter.FileListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:文件
 * Created by tck on 2017/12/9.
 */

public class FileFragment extends BaseFragment1 implements View.OnClickListener {


    private TextView mTvTitle;
    private ExpandableListView mExpandableListView;
    private List<FileListBean> mFileListBeanList = new ArrayList<>();
    private FileListAdapter mAdapter;

    public static FileFragment newInstance() {
        FileFragment fileFragment = new FileFragment();

        return fileFragment;
    }

    {
        for (int i = 0; i < 3; i++) {
            List<FileListBean.FileSubsBean> list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                FileListBean.FileSubsBean fileSubsBean = new FileListBean.FileSubsBean("文件00" + i);
                list.add(fileSubsBean);
            }
            FileListBean subjectsListBean = new FileListBean("项目文件夹00" + i, list);
            mFileListBeanList.add(subjectsListBean);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mExpandableListView.setGroupIndicator(null);
        mAdapter = new FileListAdapter(getContext(), mFileListBeanList);
        mExpandableListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
        }
    }
}
