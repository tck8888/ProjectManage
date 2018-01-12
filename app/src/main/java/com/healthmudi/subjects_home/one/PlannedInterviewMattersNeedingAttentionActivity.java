package com.healthmudi.subjects_home.one;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.view.EmptyView;
import com.lzy.okgo.OkGo;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

/**
 * decription:注意事项
 * Created by tck on 2017/12/10.
 */

public class PlannedInterviewMattersNeedingAttentionActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private TextView mTvPlannedInterviewMattersNeedingAttention;
    private EmptyView mEmptyView;
    private SmartRefreshLayout mRefreshLayout;

    private Map<String, String> map = new HashMap<>();
    private String url = "";
    private String tag = "PlannedInterviewMattersNeedingAttentionActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_interview_matters_needing_attention;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            String type = getIntent().getStringExtra(Constant.KEY_INFOMATION);
            String subject_id = getIntent().getStringExtra(Constant.KEY_SUBJECT_ID);
            ProjectListBean projectListBean = Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
            map.put("project_id", String.valueOf(projectListBean.getProject_id()));
            switch (type) {
                case MessageEvent.KEY_SAE_REPORT_SUCCESS://项目-工时-SAE上报注意事项
                    url = ProjectManageHttpUrlList.PROJECT_JOB_SAE_TIP_URL;
                    break;
                case MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_ADD_SUCCESS://入组的注意事项
                    url = ProjectManageHttpUrlList.PROJECT_SUBJECT_VISIT_TIP_URL;
                    //map.put("subject_id","");
                    map.put("visit_type", "1");//访视类型 1.入组访视、2.常规访视、3.退出访视、4.计划外访视
                    break;
                case MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS://计划外访视
                    url = ProjectManageHttpUrlList.PROJECT_SUBJECT_VISIT_TIP_URL;
                    map.put("subject_id",subject_id);
                    map.put("visit_type", "4");
                    break;
                case MessageEvent.KEY_RESEARCH_END_VISIT_SUCCESS://退出访视
                    url = ProjectManageHttpUrlList.PROJECT_SUBJECT_VISIT_TIP_URL;
                    map.put("subject_id",subject_id);
                    map.put("visit_type", "3");
                    break;
                case MessageEvent.KEY_REGULAR_VISITS_SUCCESS://常规访视
                    url = ProjectManageHttpUrlList.PROJECT_SUBJECT_VISIT_TIP_URL;
                    map.put("subject_id",subject_id);
                    map.put("visit_type", "2");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();
        mTvPlannedInterviewMattersNeedingAttention = (TextView) findViewById(R.id.tv_planned_interview_matters_needing_attention);
        mEmptyView = (EmptyView) findViewById(R.id.empty_layout);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
    }


    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(url, map, tag, new OnServerCallBack<HttpResult<String>, String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) {
                    mEmptyView.showEmptyView();
                } else {
                    mEmptyView.showContentView();
                    mTvPlannedInterviewMattersNeedingAttention.setText(result);
                }
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                mEmptyView.showEmptyView();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mTvPlannedInterviewMattersNeedingAttention = null;
            mEmptyView = null;
            mRefreshLayout = null;
            map.clear();
            map = null;
            OkGo.getInstance().cancelTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
