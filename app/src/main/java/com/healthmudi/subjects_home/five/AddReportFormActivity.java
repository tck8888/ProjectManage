package com.healthmudi.subjects_home.five;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.CenterListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.five.adapter.CenterListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增报表
 * Created by tck
 * Date: 2017/12/18 09：39
 */

public class AddReportFormActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtReportFormProjectName;
    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private AutoListView mAutoListView;
    private List<CenterListBean> mCenterListBeen = new ArrayList<>();
    private CenterListAdapter mAdapter;

    {
        mCenterListBeen.add(new CenterListBean("上海市肿瘤医院", true));
        mCenterListBeen.add(new CenterListBean("上海市中山医院", false));
        mCenterListBeen.add(new CenterListBean("上海市复旦大学附属医院", false));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_report_form;
    }

    @Override
    public void initView() {
        super.initView();
        mEtReportFormProjectName = (EditText) findViewById(R.id.et_report_form_project_name);
        mTvStartTime = (TextView) findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mAutoListView = (AutoListView) findViewById(R.id.auto_list_view);
        mAdapter = new CenterListAdapter(this, mCenterListBeen);
        mAutoListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.fl_select_start_time).setOnClickListener(this);
        findViewById(R.id.fl_select_end_time).setOnClickListener(this);
        findViewById(R.id.tv_create_form).setOnClickListener(this);
        mAutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mCenterListBeen.get(position).isChecked()) {
                    for (CenterListBean centerListBean : mCenterListBeen) {
                        centerListBean.setChecked(false);
                    }
                    mCenterListBeen.get(position).setChecked(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.fl_select_start_time:

                break;
            case R.id.fl_select_end_time:

                break;
            case R.id.tv_create_form:

                break;
        }
    }
}
