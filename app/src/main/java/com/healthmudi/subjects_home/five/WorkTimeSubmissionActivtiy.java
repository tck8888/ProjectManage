package com.healthmudi.subjects_home.five;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.subjects_home.adapter.WorkTimeSubmissionItemListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:工时提交
 * <p>
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionActivtiy extends BaseActivity implements View.OnClickListener {

    private ListView mListView;

    private List<WorkTimeSubmissionItemListBean> mWorkTimeSubmissionItemListBeen = new ArrayList<>();
    private WorkTimeSubmissionItemListAdapter mWorkTimeSubmissionItemListAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_worktime_submission;
    }

    @Override
    public void initData() {
        super.initData();
        String[] stringArray = getResources().getStringArray(R.array.worktime_submission_list);
        String[] stringArray1 = getResources().getStringArray(R.array.worktime_submission_type);
        for (int i = 0; i < stringArray.length; i++) {
            mWorkTimeSubmissionItemListBeen.add(new WorkTimeSubmissionItemListBean(false, stringArray[i], stringArray1[i]));
        }
    }

    @Override
    public void initView() {
        super.initView();
        mListView = (ListView) findViewById(R.id.list_view);
        View headView = View.inflate(this, R.layout.head_view_layout2, null);
        mListView.addHeaderView(headView);
        mWorkTimeSubmissionItemListAdapter = new WorkTimeSubmissionItemListAdapter(this, mWorkTimeSubmissionItemListBeen);
        mListView.setAdapter(mWorkTimeSubmissionItemListAdapter);

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean = mWorkTimeSubmissionItemListBeen.get(position - 1);
                openActivity(workTimeSubmissionItemListBean);
            }
        });
    }

    private void openActivity(WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean) {
        Class clazz = null;
        switch (workTimeSubmissionItemListBean.type) {
            case "institution_establishment":
                clazz = InstitutionEstablishmentActivity.class;
                break;
            case "ethical_submission":
                clazz = EthicalSubmissionActivity.class;
                break;
            case "contract_follow_up":
                clazz = ContractFollowUpActivity.class;
                break;
            case "Project_start_meeting":
                clazz = ProjectStartMeetingActivity.class;
                break;
            case "sae_report":
                clazz = SaeReportActivity.class;
                break;
            case "presifting":
                break;
            case "visitors_visit_to_the_rules":
                break;
            case "edc_fill_in":
                break;
        }
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.iv_check_mark:
                break;
        }
    }
}
