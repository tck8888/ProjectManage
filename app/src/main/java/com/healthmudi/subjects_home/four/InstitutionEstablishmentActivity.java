package com.healthmudi.subjects_home.four;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * decription:机构立项
 * Created by tck on 2017/12/11.
 */

public class InstitutionEstablishmentActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private LinearLayout mLlSubmitDate;
    private TextView mTvSubmitDate;
    private LinearLayout mLlApprovedDate;
    private TextView mTvApprovedDate;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;

    private Map<String ,String> map = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_institution_establishment;
    }

    @Override
    public void initData() {
        super.initData();
        map.put("project_id","");
        map.put("site_id","");

    }


    @Override
    public void initView() {
        super.initView();

        mTvProjectName = (TextView) findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) findViewById(R.id.tv_center_name);

        mTvSubmitDate = (TextView) findViewById(R.id.tv_submit_date);
        mTvApprovedDate = (TextView) findViewById(R.id.tv_approved_date);
        mEtRemark = (EditText) findViewById(R.id.et_remark);

        initTimePick();
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v.getId() == R.id.tv_submit_date) {
                    mTvSubmitDate.setText(DateUtils.getTime(date));
                } else if (v.getId() == R.id.tv_approved_date) {
                    mTvApprovedDate.setText(DateUtils.getTime(date));
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleBgColor(getResources().getColor(R.color.color_f3f3f3))
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setSubmitColor(getResources().getColor(R.color.color_1abc9c))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_464c5b))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.color_e4e4e4))
                .build();
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_submit_date).setOnClickListener(this);
        findViewById(R.id.ll_approved_date).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_submit_date:
                mTimePickerView.show(mTvSubmitDate);
                break;
            case R.id.ll_approved_date:
                mTimePickerView.show(mTvApprovedDate);
                break;
        }
    }

    private void submitData() {
        String site_submit_date = mTvSubmitDate.getText().toString().trim();
        String site_approve_date = mTvApprovedDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        map.put("site_submit_date","");
        map.put("site_approve_date","");
        map.put("status","");
        map.put("job_time","");
        map.put("remark","");
    }
}
