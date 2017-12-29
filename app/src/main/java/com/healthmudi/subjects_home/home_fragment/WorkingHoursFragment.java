package com.healthmudi.subjects_home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.WorkTimeSubmissionActivtiy;
import com.healthmudi.subjects_home.four.dialog.InstitutionEstablishmentDialog;
import com.healthmudi.subjects_home.home_fragment.adapter.WorkingHoursListAdapter;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:工时
 * Created by tck on 2017/12/9.
 */

public class WorkingHoursFragment extends BaseFragment1 implements View.OnClickListener, OnRefreshListener {

    private String mProject_id;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;
    private ListView mListView;

    private List<WorkingHoursListBean> mWorkingHoursListBeanList = new ArrayList<>();
    private WorkingHoursListAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();
    private String tag = "WorkingHoursFragment";

    public static WorkingHoursFragment newInstance(String project_id) {
        WorkingHoursFragment workingHoursFragment = new WorkingHoursFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PROJECT_ID, project_id);
        workingHoursFragment.setArguments(bundle);
        return workingHoursFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_working_hours;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mProject_id = arguments.getString(Constant.KEY_PROJECT_ID);
        map.put("project_id", mProject_id);
    }

    @Override
    protected void initView(@Nullable View view) {

        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mListView = (ListView) view.findViewById(R.id.list_view);

        mAdapter = new WorkingHoursListAdapter(getContext(), mWorkingHoursListBeanList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_add_subjects).setOnClickListener(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkingHoursListBean workingHoursListBean = mWorkingHoursListBeanList.get(position);
                openDialog(workingHoursListBean);
            }
        });
    }

    private void openDialog(WorkingHoursListBean workingHoursListBean) {
        switch (workingHoursListBean.getJob_type_id()) {
            case 1:
                InstitutionEstablishmentDialog institutionEstablishmentDialog = new InstitutionEstablishmentDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN,workingHoursListBean);
                institutionEstablishmentDialog.setArguments(bundle);
                institutionEstablishmentDialog.show(getFragmentManager(),"InstitutionEstablishmentDialog");
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 99:
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_JOB_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<WorkingHoursListBean>>, List<WorkingHoursListBean>>() {
            @Override
            public void onSuccess(List<WorkingHoursListBean> result) {
                if (!ListUtil.isEmpty(mWorkingHoursListBeanList)) {
                    mWorkingHoursListBeanList.clear();
                }
                mWorkingHoursListBeanList.addAll(result);
                if (ListUtil.isEmpty(mWorkingHoursListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mWorkingHoursListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_add_subjects:
                Intent intent = new Intent(getContext(), WorkTimeSubmissionActivtiy.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }
}
