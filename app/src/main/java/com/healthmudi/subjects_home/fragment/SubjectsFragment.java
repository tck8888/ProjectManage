package com.healthmudi.subjects_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.subjects_home.one.SubjectsPersonalActivity;
import com.healthmudi.subjects_home.one.SubjectsPersonalSerachActivity;
import com.healthmudi.subjects_home.adapter.SubjectsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:受试者fragment
 * Created by tck on 2017/12/9.
 */

public class SubjectsFragment extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvTitle;
    private ExpandableListView mExpandableListView;
    private List<SubjectsListBean> mSubjectsListBeanList = new ArrayList<>();
    private SubjectsListAdapter mAdapter;

    public static SubjectsFragment newInstance() {
        SubjectsFragment subjectsFragment = new SubjectsFragment();

        return subjectsFragment;
    }

    {
        for (int i = 0; i < 3; i++) {
            List<SubjectsListBean.SubjectsListSubBean> list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                SubjectsListBean.SubjectsListSubBean subjectsListSubBean = new SubjectsListBean.SubjectsListSubBean("测试" + i + "的第" + j + "个子条目");
                list.add(subjectsListSubBean);
            }
            SubjectsListBean subjectsListBean = new SubjectsListBean("测试" + i, list);
            mSubjectsListBeanList.add(subjectsListBean);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subjects;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mExpandableListView.setGroupIndicator(null);
        mAdapter = new SubjectsListAdapter(getContext(), mSubjectsListBeanList);
        mExpandableListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);

        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_subjects_search).setOnClickListener(this);
        view.findViewById(R.id.iv_add_subjects).setOnClickListener(this);

        mAdapter.setOnItemClick(new SubjectsListAdapter.OnItemClick() {
            @Override
            public void click(int groupPosition, int childPosition, String type) {
                if ("delete".equals(type)) {
                    mSubjectsListBeanList.get(groupPosition).mSubjectsListSubBeen.remove(childPosition);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(getContext(), SubjectsPersonalActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
            case R.id.iv_subjects_search:
                Intent intent = new Intent(getContext(), SubjectsPersonalSerachActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_add_subjects:
                break;
        }
    }
}
