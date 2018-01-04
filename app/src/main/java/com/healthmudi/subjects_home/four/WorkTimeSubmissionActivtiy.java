package com.healthmudi.subjects_home.four;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.home_fragment.adapter.WorkTimeSubmissionItemListAdapter;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:工时提交
 * <p>
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionActivtiy extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private SmartRefreshLayout mRefreshLayout;
    private AutoListView mNormalWorkListView;
    private AutoListView mSpecialWorkListView;
    private AutoListView mOtherWorkListView;

    private List<WorkTimeSubmissionItemListBean> mNormalWorkListBean = new ArrayList<>();
    private List<WorkTimeSubmissionItemListBean> mSpecialWorkListBean = new ArrayList<>();
    private List<WorkTimeSubmissionItemListBean> mOtherWorkListBean = new ArrayList<>();

    private WorkTimeSubmissionItemListAdapter mAdapter1;
    private WorkTimeSubmissionItemListAdapter mAdapter2;
    private WorkTimeSubmissionItemListAdapter mAdapter3;

    private Map<String, String> map = new HashMap<>();

    private String tag = "WorkTimeSubmissionActivtiy";

    @Override
    public int getLayoutId() {
        return R.layout.activity_worktime_submission;
    }

    @Override
    public void initData() {
        super.initData();
        ProjectListBean projectListBean = Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
        map.put("project_id", String.valueOf(projectListBean.getProject_id()));
    }

    @Override
    public void initView() {
        super.initView();
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mNormalWorkListView = (AutoListView) findViewById(R.id.normal_work_list_view);
        mSpecialWorkListView = (AutoListView) findViewById(R.id.special_work_list_view);
        mOtherWorkListView = (AutoListView) findViewById(R.id.other_work_list_view);
        mAdapter1 = new WorkTimeSubmissionItemListAdapter(this, mNormalWorkListBean);
        mAdapter2 = new WorkTimeSubmissionItemListAdapter(this, mSpecialWorkListBean);
        mAdapter3 = new WorkTimeSubmissionItemListAdapter(this, mOtherWorkListBean);
        mNormalWorkListView.setAdapter(mAdapter1);
        mSpecialWorkListView.setAdapter(mAdapter2);
        mOtherWorkListView.setAdapter(mAdapter3);

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();

        mNormalWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(mNormalWorkListBean.get(position));
            }
        });
        mSpecialWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(mSpecialWorkListBean.get(position));
            }
        });
        mOtherWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(mOtherWorkListBean.get(position));
            }
        });
    }

    private void openActivity(WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean) {
        Class clazz = null;
        switch (workTimeSubmissionItemListBean.getJob_type_id()) {
            case 1:
                clazz = InstitutionEstablishmentActivity.class;
                break;
            case 2:
                clazz = EthicalSubmissionActivity.class;
                break;
            case 3:
                clazz = ContractFollowUpActivity.class;
                break;
            case 4:
                clazz = ProjectStartMeetingActivity.class;
                break;
            case 5:
                clazz = SaeReportActivity.class;
                break;
            case 6:
                clazz = PresiftingActivity.class;
                break;
            case 7:
                clazz = VisitorsVisitToTheRulesActivity.class;
                break;
            case 8:
                clazz = EDCFillInActivity.class;
                break;
            case 9:
                clazz = ServerConfActivity.class;
                break;
            case 99:
                clazz = OtherWorkActivity.class;
                break;
        }
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(Constant.KEY_WORKTIME_SUBMISSION_ITEM_LIST_BEAN, workTimeSubmissionItemListBean);
            startActivity(intent);
            activityFinish();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_JOB_ITEMS_URL, map, tag, new OnServerCallBack<HttpResult<List<WorkTimeSubmissionItemListBean>>, List<WorkTimeSubmissionItemListBean>>() {
            @Override
            public void onSuccess(List<WorkTimeSubmissionItemListBean> result) {
                if (!mNormalWorkListBean.isEmpty()) {
                    mNormalWorkListBean.clear();
                }
                if (!mSpecialWorkListBean.isEmpty()) {
                    mSpecialWorkListBean.clear();
                }
                if (!mOtherWorkListBean.isEmpty()) {
                    mOtherWorkListBean.clear();
                }
                for (WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean : result) {
                    if (workTimeSubmissionItemListBean.getJob_type_id() == 9) {
                        mSpecialWorkListBean.add(workTimeSubmissionItemListBean);
                    } else if (workTimeSubmissionItemListBean.getJob_type_id() == 99) {
                        mOtherWorkListBean.add(workTimeSubmissionItemListBean);
                    } else {
                        mNormalWorkListBean.add(workTimeSubmissionItemListBean);
                    }
                }
                mAdapter1.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
                mAdapter3.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();

            }

            @Override
            public void onFailure(int code, String mesage) {
                mRefreshLayout.finishRefresh();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
        }
    }

}
