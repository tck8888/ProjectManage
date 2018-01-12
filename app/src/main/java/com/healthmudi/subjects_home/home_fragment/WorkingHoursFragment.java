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
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.WorkHourDetailActivity;
import com.healthmudi.subjects_home.four.WorkTimeSubmissionListActivtiy;
import com.healthmudi.subjects_home.home_fragment.adapter.WorkingHoursListAdapter;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:工时
 * Created by tck on 2017/12/9.
 */

public class WorkingHoursFragment extends BaseFragment1 implements View.OnClickListener,
        OnRefreshListener,
        OnLoadmoreListener {

    private String mProject_id;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;
    private ListView mListView;

    private List<WorkingHoursListBean> mWorkingHoursListBeanList = new ArrayList<>();
    private WorkingHoursListAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();
    private int page = 0;
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
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        mRefreshLayout.setOnLoadmoreListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkingHoursListBean workingHoursListBean = mWorkingHoursListBeanList.get(position);
                openActivity(workingHoursListBean);
            }
        });
    }

    private void openActivity(WorkingHoursListBean workingHoursListBean) {
        Intent intent = new Intent(getContext(), WorkHourDetailActivity.class);
        intent.putExtra(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_INSTITUTION_ESTABLISHMENT_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_ETHICAL_SUBMISSION_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_CONTRACT_FOLLOW_UP_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_PROJECT_START_MEETING_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_SAE_REPORT_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_PRESIFTING_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_VISITORS_VISIT_TO_THE_RULES_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_EDC_FILL_IN_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_OTHER_WORK_SUCCESS) ||
                    event.getTag().equals(MessageEvent.KEY_SERVER_CONF_SUCCESS)) {
                mRefreshLayout.autoRefresh();
            }
        }

    }

    private boolean isRefresh = true;

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isRefresh = true;
        page = 0;
        getData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        isRefresh = false;
        page++;
        getData();
    }

    private void getData() {
        map.put("page", String.valueOf(page));
        HttpRequest.getInstance().get(ProjectManageHttpUrlList.PROJECT_JOB_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<WorkingHoursListBean>>, List<WorkingHoursListBean>>() {
            @Override
            public void onSuccess(List<WorkingHoursListBean> result) {

                if (!ListUtil.isEmpty(mWorkingHoursListBeanList) && isRefresh) {
                    mWorkingHoursListBeanList.clear();
                }
                if (!ListUtil.isEmpty(result)) {
                    mWorkingHoursListBeanList.addAll(result);
                }
                if (ListUtil.isEmpty(mWorkingHoursListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                if (isRefresh) {
                    mRefreshLayout.finishRefresh();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mWorkingHoursListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                if (isRefresh) {
                    mRefreshLayout.finishRefresh();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
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
                Intent intent = new Intent(getContext(), WorkTimeSubmissionListActivtiy.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
