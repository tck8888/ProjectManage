package com.healthmudi.home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.ExitProjectManagerActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.home.home_fragment.adapter.ProjectListAdapter;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.SubjectsHomeActivity;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目fragment
 * Created by tck on 2017/12/8.
 */
public class ProjectFragment extends BaseFragment1 implements View.OnClickListener, OnRefreshListener {

    private ListView mListView;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;

    private ProjectListAdapter mAdapter;

    private List<ProjectListBean> mProjectListBeen = new ArrayList<>();
    private String tag = "ProjectFragment";

    public static ProjectFragment newInstance() {
        ProjectFragment projectFragment = new ProjectFragment();

        return projectFragment;
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
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);

        View headView = View.inflate(getContext(), R.layout.head_view_layout, null);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mListView.addHeaderView(headView);

        mAdapter = new ProjectListAdapter(getContext(), mProjectListBeen);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void setViewData() {
        super.setViewData();
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), SubjectsHomeActivity.class);
                intent.putExtra(Constant.KEY_PROJECT_LIST_BEAN, mProjectListBeen.get(position - 1));
                startActivity(intent);
            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                startActivity(new Intent(getContext(), ExitProjectManagerActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }


    public void getData() {
        HttpRequest.getInstance()
                .get(HttpUrlList.PROJECT_LIST_URL, null, tag, new OnServerCallBack<HttpResult<List<ProjectListBean>>, List<ProjectListBean>>() {
                    @Override
                    public void onSuccess(List<ProjectListBean> result) {
                        if (!ListUtil.isEmpty(mProjectListBeen)) {
                            mProjectListBeen.clear();
                        }
                        mProjectListBeen.addAll(result);
                        if (ListUtil.isEmpty(mProjectListBeen)) {
                            mEmptyLayout.showEmptyView();
                        } else {
                            mEmptyLayout.showContentView();
                        }
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(int code, String mesage) {
                        if (ListUtil.isEmpty(mProjectListBeen)) {
                            mEmptyLayout.showEmptyView();
                            mEmptyLayout.setEmptyText(mesage);
                        } else {
                            mEmptyLayout.showContentView();
                        }
                        mRefreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            OkGo.getInstance().cancelTag(tag);
            if (!ListUtil.isEmpty(mProjectListBeen)) {
                ListUtil.clearList(mProjectListBeen);
            }
            mAdapter = null;
            mListView = null;
            mRefreshLayout = null;
            mEmptyLayout = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
