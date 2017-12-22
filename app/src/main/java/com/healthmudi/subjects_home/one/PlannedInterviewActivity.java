package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * decription:计划外访式
 * Created by tck on 2017/12/10.
 */
public class PlannedInterviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvSelectResearchCenter;
    private TextView mTvInputNumber;
    private TextView mTvInitials;
    private TextView mTvVisitDate;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;
    private IosDialog mIosDialog;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private Map<String, String> map = new HashMap<>();
    private String tag = "PlannedInterviewActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_interview;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mSubjectsBean = (SubjectsListBean.SubjectsBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("project_id", String.valueOf(mSubjectsBean.getProject_id()));
        map.put("subject_id", String.valueOf(mSubjectsBean.getSubject_id()));
    }

    @Override
    public void initView() {
        super.initView();

        initTimePick();
        initDialog();

        mTvSelectResearchCenter = (TextView) findViewById(R.id.tv_select_research_center);
        mTvInputNumber = (TextView) findViewById(R.id.tv_input_number);
        mTvInitials = (TextView) findViewById(R.id.tv_initials);
        mTvVisitDate = (TextView) findViewById(R.id.tv_visit_date);
        mEtRemark = (EditText) findViewById(R.id.et_remark);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null) {
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInputNumber.setText(mSubjectsBean.getSubject_code());
            mTvInitials.setText(mSubjectsBean.getName_py());
        }
    }

    public void initTimePick() {

        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvVisitDate.setText(DateUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确认")
                .setContentSize(16)
                .setTitleBgColor(getResources().getColor(R.color.color_f3f3f3))
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setSubmitColor(getResources().getColor(R.color.color_1abc9c))
                .setCancelColor(getResources().getColor(R.color.color_464c5b))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)
                .setDividerColor(getResources().getColor(R.color.color_e4e4e4))
                .build();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(this)
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("计划外访式成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        //TODO:
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS));
                        finish();
                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_visit_date).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_visit_date:
                mTimePickerView.show();
                break;
        }
    }

    /**
     * decription:提交数据
     */
    private void submitData() {
        String actual_visit_time = mTvVisitDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();
        if (TextUtils.isEmpty(actual_visit_time)) {
            Toast.makeText(this, "请选择访视日期", Toast.LENGTH_SHORT).show();
            return;
        }

        map.put("actual_visit_time", actual_visit_time);
        map.put("remark", remark);
        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_VISIT_OUTPLAN_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(PlannedInterviewActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(PlannedInterviewActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(PlannedInterviewActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
