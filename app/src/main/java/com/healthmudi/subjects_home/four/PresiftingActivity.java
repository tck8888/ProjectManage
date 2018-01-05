package com.healthmudi.subjects_home.four;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:受试者筛选
 * Created by tck on 2017/12/11.
 */

public class PresiftingActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvOperationDate;
    private EditText mEtPrescreenCount;
    private EditText mEtMeetCount;
    private TextView mTvJobTime;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;
    private IosDialog mIosDialog;

    private Map<String, String> map = new HashMap<>();

    private List<String> mStringList = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();

    private ProjectListBean mProjectListBean;

    private String site_id = "";

    private String tag = "PresiftingActivity";

    @Override
    public int getLayoutId() {
        return R.layout.fargment_presifting_fragment;
    }


    @Override
    public void initData() {
        super.initData();

        try {
            String[] strings = getResources().getStringArray(R.array.work_hour_array);
            mStringList.addAll(Arrays.asList(strings));

            mProjectListBean = (ProjectListBean) Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
            map.put("project_id", String.valueOf(mProjectListBean.getProject_id()));
            mSiteBeanList.addAll(mProjectListBean.getSite());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();

        mTvProjectName = (TextView) findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) findViewById(R.id.tv_center_name);
        mTvOperationDate = (TextView) findViewById(R.id.tv_operation_date);
        mEtPrescreenCount = (EditText) findViewById(R.id.et_prescreen_count);
        mEtMeetCount = (EditText) findViewById(R.id.et_meet_count);
        mTvJobTime = (TextView) findViewById(R.id.tv_job_time);
        mEtRemark = (EditText) findViewById(R.id.et_remark);

        initTimePick();
        initWorkHourPick();
        initDialog();
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v.getId() == R.id.tv_operation_date) {
                    mTvOperationDate.setText(DateUtils.getTime(date));
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

    public void initWorkHourPick() {
        mOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (v.getId() == R.id.tv_job_time) {
                    String s = mStringList.get(options1);
                    mTvJobTime.setText(s);
                } else if (v.getId() == R.id.tv_center_name) {
                    ProjectListBean.SiteBean siteBean = mSiteBeanList.get(options1);
                    site_id = String.valueOf(siteBean.getSite_id());
                    mTvCenterName.setText(siteBean.getSite_name());
                }

            }
        }).setCancelText("取消")
                .setSubmitText("确认")
                .setTitleBgColor(getResources().getColor(R.color.color_f3f3f3))
                .setOutSideCancelable(true)
                .setSubmitColor(getResources().getColor(R.color.color_1abc9c))
                .setCancelColor(getResources().getColor(R.color.color_464c5b))
                .setDividerColor(getResources().getColor(R.color.color_e4e4e4))
                .setContentTextSize(16)
                .build();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(this)
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("工时提交工时成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_PRESIFTING_SUCCESS));
                        activityFinish();
                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
    }


    @Override
    public void setViewData() {
        super.setViewData();
        if (mProjectListBean != null) {
            mTvProjectName.setText(mProjectListBean.getProject_name());
        }
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_center_name).setOnClickListener(this);
        findViewById(R.id.ll_operation_date).setOnClickListener(this);
        findViewById(R.id.ll_job_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_center_name:
                if (mSiteBeanList.isEmpty()) {
                    Toast.makeText(this, "暂无研究中心", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mSiteBeanList);
                    mOptionsPickerView.show(mTvCenterName);
                }
                break;
            case R.id.ll_operation_date:
                mTimePickerView.show(mTvOperationDate);
                break;
            case R.id.ll_job_time:
                mOptionsPickerView.setPicker(mStringList);
                mOptionsPickerView.show(mTvJobTime);
                break;
        }
    }

    private void submitData() {


        String operation_date = mTvOperationDate.getText().toString().trim();
        String prescreen_count = mEtPrescreenCount.getText().toString().trim();
        String meet_count = mEtMeetCount.getText().toString().trim();
        String job_time = mTvJobTime.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(operation_date, prescreen_count, meet_count, job_time)) return;
        map.put("site_id", site_id);
        map.put("operation_date", operation_date);
        map.put("prescreen_count", prescreen_count);
        map.put("meet_count", meet_count);
        map.put("job_time", job_time);
        map.put("remark", remark);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_JOB_SUBJECT_FILTER_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(PresiftingActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(PresiftingActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(PresiftingActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkData(String operation_date, String prescreen_count, String meet_count, String job_time) {
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(this, "请选择中心名称", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(operation_date)) {
            Toast.makeText(this, "请选择预筛日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(prescreen_count)) {
            Toast.makeText(this, "请输入患者数", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(meet_count)) {
            Toast.makeText(this, "请输入初步符合患者数", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time)) {
            Toast.makeText(this, "请选择用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
