package com.healthmudi.subjects_home.four;

import android.content.Intent;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.home_fragment.adapter.WorkTimeSubmissionItemListAdapter;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:工时提交
 * <p>
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionActivtiy extends BaseActivity implements View.OnClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;
    private AutoListView mNormalWorkListView;
    private AutoListView mSpecialWorkListView;
    private AutoListView mOtherWorkListView;

    private List<WorkTimeSubmissionItemListBean> mWorkTimeSubmissionItemListBeen = new ArrayList<>();
    private WorkTimeSubmissionItemListAdapter mWorkTimeSubmissionItemListAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_worktime_submission;
    }

    @Override
    public void initData() {
        super.initData();


    }

    @Override
    public void initView() {
        super.initView();

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) findViewById(R.id.empty_layout);
        mNormalWorkListView = (AutoListView) findViewById(R.id.normal_work_list_view);
        mSpecialWorkListView = (AutoListView) findViewById(R.id.special_work_list_view);
        mOtherWorkListView = (AutoListView) findViewById(R.id.other_work_list_view);

        View headView = View.inflate(this, R.layout.head_view_layout2, null);

        mWorkTimeSubmissionItemListAdapter = new WorkTimeSubmissionItemListAdapter(this, mWorkTimeSubmissionItemListBeen);


    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);


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
            case 10:
                clazz = OtherWorkActivity.class;
                break;
        }
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(Constant.KEY_WORKTIME_SUBMISSION_ITEM_LIST_BEAN, workTimeSubmissionItemListBean);
            startActivity(intent);
        }
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
