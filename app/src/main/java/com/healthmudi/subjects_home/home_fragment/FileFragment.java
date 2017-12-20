package com.healthmudi.subjects_home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.healthmudi.ProjectWebViewActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.FileListBean;
import com.healthmudi.bean.WebViewBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.home_fragment.adapter.FileListAdapter;
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
 * decription:文件
 * Created by tck on 2017/12/9.
 */

public class FileFragment extends BaseFragment1 implements View.OnClickListener, OnRefreshListener {

    private SmartRefreshLayout mRefreshLayout;
    private ExpandableListView mExpandableListView;
    private EmptyView mEmptyLayout;

    private List<FileListBean> mFileListBeanList = new ArrayList<>();
    private FileListAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();
    private String tag = "FileFragment";
    private String mProject_id;

    public static FileFragment newInstance(String project_id) {
        FileFragment fileFragment = new FileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PROJECT_ID, project_id);
        fileFragment.setArguments(bundle);
        return fileFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file;
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
                FileListBean.FilesBean filesBean = mFileListBeanList.get(groupPosition).getFiles().get(childPosition);
                Intent intent = new Intent(getContext(), ProjectWebViewActivity.class);
                WebViewBean webViewBean = new WebViewBean(filesBean.getFile_name(), filesBean.getFile_path());
                intent.putExtra(Constant.KEY_WEBVIEW_BEAN, webViewBean);
                startActivity(intent);
                return false;
            }
        });

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_SUBJECT_FILE_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<FileListBean>>, List<FileListBean>>() {
            @Override
            public void onSuccess(List<FileListBean> data) {
                if (!ListUtil.isEmpty(mFileListBeanList)) {
                    mFileListBeanList.clear();
                }
                mFileListBeanList.addAll(data);
                if (ListUtil.isEmpty(mFileListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mFileListBeanList)) {
                    mEmptyLayout.showEmptyView();
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
            mExpandableListView = null;
            mRefreshLayout = null;
            mAdapter = null;
            map.clear();
            map = null;
            mFileListBeanList.clear();
            mFileListBeanList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
