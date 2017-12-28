package com.healthmudi.subjects_home.four;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.subjects_home.home_fragment.adapter.WorkTimeSubmissionItemListAdapter;

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

        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].contains("后台配置特殊工作")) {
                mWorkTimeSubmissionItemListBeen.add(new WorkTimeSubmissionItemListBean(i + 1, stringArray[i], "special"));
            } else if (stringArray[i].contains("其它工作")) {
                mWorkTimeSubmissionItemListBeen.add(new WorkTimeSubmissionItemListBean(i + 1, stringArray[i], "other"));
            } else {
                mWorkTimeSubmissionItemListBeen.add(new WorkTimeSubmissionItemListBean(i + 1, stringArray[i], "normal"));
            }
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
        switch (workTimeSubmissionItemListBean.getId()) {
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
                clazz = OtherWorkActivity.class;
                break;
            case 10:
                clazz = ServerConfActivity.class;
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
                activityFinish();
                break;
        }
    }
}
