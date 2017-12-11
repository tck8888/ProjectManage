package com.healthmudi.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.SubjectsHomeActivity;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.CommonItemAdapter;
import com.healthmudi.entity.CommonItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目fragment
 * Created by tck on 2017/12/8.
 */
public class ProjectFragment extends BaseFragment1 implements View.OnClickListener {

    private ListView mListView;

    private List<CommonItemBean> mCommonItemBeanList = new ArrayList<>();

    public static ProjectFragment newInstance() {
        ProjectFragment projectFragment = new ProjectFragment();

        return projectFragment;
    }

    {
        for (int i = 0; i < 10; i++) {
            mCommonItemBeanList.add(new CommonItemBean("测试数据" + i));
        }
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(@Nullable View view) {
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);

        View headView = View.inflate(getContext(), R.layout.head_view_layout, null);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.addHeaderView(headView);

        mListView.setAdapter(new CommonItemAdapter(getContext(), mCommonItemBeanList));
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), SubjectsHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                break;
        }
    }
}
