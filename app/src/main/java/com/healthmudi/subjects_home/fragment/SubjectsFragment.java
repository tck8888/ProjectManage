package com.healthmudi.subjects_home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.adapter.SubjectsListAdapter;
import com.healthmudi.subjects_home.one.SubjectsPersonalActivity;
import com.healthmudi.subjects_home.one.SubjectsPersonalSerachActivity;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:受试者fragment
 * Created by tck on 2017/12/9.
 */

public class SubjectsFragment extends BaseFragment1 implements View.OnClickListener, OnRefreshListener {

    private ExpandableListView mExpandableListView;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;

    private List<SubjectsListBean> mSubjectsListBeanList = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    private SubjectsListAdapter mAdapter;
    private String tag = "SubjectsFragment";
    private String mProject_id;


    public static SubjectsFragment newInstance(String project_id) {
        SubjectsFragment subjectsFragment = new SubjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PROJECT_ID, project_id);
        subjectsFragment.setArguments(bundle);
        return subjectsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subjects;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mProject_id = arguments.getString(Constant.KEY_PROJECT_ID);
        map.put("project_id", mProject_id);
    }

    @Override
    protected void initView(@Nullable View view) {
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
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
                    mSubjectsListBeanList.get(groupPosition).getSubjects().remove(childPosition);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(getContext(), SubjectsPersonalActivity.class);
                    startActivity(intent);
                }

            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_SUBJECT_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<SubjectsListBean>>, List<SubjectsListBean>>() {
            @Override
            public void onSuccess(List<SubjectsListBean> result) {
                if (!ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mSubjectsListBeanList.clear();
                }
                mSubjectsListBeanList.addAll(result);
                if (ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }

                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mEmptyLayout = null;
            OkGo.getInstance().cancelTag(tag);
            mExpandableListView = null;
            mRefreshLayout = null;
            mAdapter = null;
            map.clear();
            map = null;
            mSubjectsListBeanList.clear();
            mSubjectsListBeanList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
