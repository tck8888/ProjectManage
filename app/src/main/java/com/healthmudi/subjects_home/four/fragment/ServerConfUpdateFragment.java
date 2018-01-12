package com.healthmudi.subjects_home.four.fragment;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.bean.WorkingHoursListBean;
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
 * Created by tck
 * Date: 2018/01/05 11：06
 */

public class ServerConfUpdateFragment extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvOperationDate;

    private TextView mTvJobCount;
    private TextView mTvJobTime;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;
    private IosDialog mIosDialog;

    private Map<String, String> map = new HashMap<>();

    private List<String> mStringList = new ArrayList<>();
    private List<String> mDataList = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();

    private ProjectListBean mProjectListBean;
    private WorkingHoursListBean mWorkingHoursListBean;

    private String site_id = "";

    private String tag = "ServerConfUpdateFragment";
    private WorkTimeSubmissionItemListBean mWorkTimeSubmissionItemListBean;

    public static ServerConfUpdateFragment newInstance(WorkingHoursListBean workingHoursListBean,WorkTimeSubmissionItemListBean workTimeSubmissionItemListBean) {
        ServerConfUpdateFragment serverConfUpdateFragment = new ServerConfUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        bundle.putSerializable(Constant.KEY_WORKTIME_SUBMISSION_ITEM_LIST_BEAN, workTimeSubmissionItemListBean);
        serverConfUpdateFragment.setArguments(bundle);
        return serverConfUpdateFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {
            mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);

            for (int i = 1; i <= 10; i++) {
                mDataList.add(String.valueOf(i));
            }
            String[] strings = getResources().getStringArray(R.array.work_hour_array);
            mStringList.addAll(Arrays.asList(strings));
            mProjectListBean = (ProjectListBean) Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
            map.put("project_id", String.valueOf(mProjectListBean.getProject_id()));
            mSiteBeanList.addAll(mProjectListBean.getSite());
            mWorkTimeSubmissionItemListBean = (WorkTimeSubmissionItemListBean) arguments.getSerializable(Constant.KEY_WORKTIME_SUBMISSION_ITEM_LIST_BEAN);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_server_conf_update;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvOperationDate = (TextView) view.findViewById(R.id.tv_operation_date);
        mTvJobCount = (TextView) view.findViewById(R.id.tv_job_count);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mEtRemark = (EditText) view.findViewById(R.id.et_remark);

        initTimePick();
        initWorkHourPick();
        initDialog();
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mProjectListBean != null) {
            mTvProjectName.setText(mProjectListBean.getProject_name());
        }
        if (mWorkingHoursListBean != null) {
            mTvProjectName.setText(mWorkingHoursListBean.getProject_name());
            mTvCenterName.setText(mWorkingHoursListBean.getSite_name());
            for (ProjectListBean.SiteBean siteBean : mSiteBeanList) {
                if (siteBean.getSite_name().equals(mWorkingHoursListBean.getSite_name())) {
                    site_id = String.valueOf(siteBean.getSite_id());
                    break;
                }
            }
            if (mWorkingHoursListBean.getOperation_date() != 0) {
                mTvOperationDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getOperation_date()));
            }
            if (mWorkingHoursListBean.getCrf_pages() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getCrf_pages()));
            }
            if (mWorkingHoursListBean.getJob_count() != 0) {
                mTvJobCount.setText(String.valueOf(mWorkingHoursListBean.getJob_count()));
            }

            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getJob_time()));
            }
            if (!TextUtils.isEmpty(mWorkingHoursListBean.getRemark())) {
                mEtRemark.setText(mWorkingHoursListBean.getRemark());
            }
        }
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
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
                .setTextColorCenter(getResources().getColor(R.color.color_1abc9c))
                .build();
    }

    public void initWorkHourPick() {
        mOptionsPickerView = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (v.getId() == R.id.tv_job_time) {
                    String s = mStringList.get(options1);
                    mTvJobTime.setText(s);
                } else if (v.getId() == R.id.tv_center_name) {
                    ProjectListBean.SiteBean siteBean = mSiteBeanList.get(options1);
                    site_id = String.valueOf(siteBean.getSite_id());
                    mTvCenterName.setText(siteBean.getSite_name());
                } else if (v.getId() == R.id.tv_job_count) {
                    String s = mDataList.get(options1);
                    mTvJobCount.setText(s);
                }

            }
        }).setCancelText("取消")
                .setSubmitText("确认")
                .setTitleBgColor(getResources().getColor(R.color.color_f3f3f3))
                .setOutSideCancelable(true)
                .setSubmitColor(getResources().getColor(R.color.color_1abc9c))
                .setCancelColor(getResources().getColor(R.color.color_464c5b))
                .setDividerColor(getResources().getColor(R.color.color_e4e4e4))
                .setTextColorCenter(getResources().getColor(R.color.color_1abc9c))
                .setContentTextSize(16)
                .build();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(getContext())
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("工时提交工时成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {

                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
        mIosDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_PRESIFTING_SUCCESS));
                activityFinish();
            }
        });
    }


    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);

        view.findViewById(R.id.ll_center_name).setOnClickListener(this);
        view.findViewById(R.id.ll_operation_date).setOnClickListener(this);
        view.findViewById(R.id.ll_job_time).setOnClickListener(this);
        view.findViewById(R.id.ll_job_count).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideSoftKeyBord();
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_center_name:
                if (mSiteBeanList.isEmpty()) {
                    Toast.makeText(getContext(), "暂无研究中心", Toast.LENGTH_SHORT).show();
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
                if (!TextUtils.isEmpty(mTvJobTime.getText().toString().trim())) {
                    int i = mStringList.indexOf(mTvJobTime.getText().toString().trim());
                    mOptionsPickerView.setSelectOptions(i);
                }
                mOptionsPickerView.show(mTvJobTime);
                break;
            case R.id.ll_job_count:
                mOptionsPickerView.setPicker(mDataList);
                if (!TextUtils.isEmpty(mTvJobCount.getText().toString().trim())) {
                    int i = mDataList.indexOf(mTvJobCount.getText().toString().trim());
                    mOptionsPickerView.setSelectOptions(i);
                }
                mOptionsPickerView.show(mTvJobCount);
                break;
        }
    }

    public void submitData() {

        String operation_date = mTvOperationDate.getText().toString().trim();
        String job_count = mTvJobCount.getText().toString().trim();
        String job_time = mTvJobTime.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(operation_date, job_count, job_time)) return;
        map.put("site_id", site_id);
        map.put("job_count", job_count);
        if (mWorkTimeSubmissionItemListBean != null) {
            map.put("job_type_name", mWorkTimeSubmissionItemListBean.getJob_type_name());
        } else {
            map.put("job_type_name", mWorkingHoursListBean.getJob_type_name());
        }
        map.put("operation_date", operation_date);
        map.put("job_time", job_time);
        map.put("remark", remark);

        if (mWorkingHoursListBean != null) {
            map.put("job_id", String.valueOf(mWorkingHoursListBean.getJob_id()));
        } else {
            map.put("job_id", "0");
        }

        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(ProjectManageHttpUrlList.PROJECT_JOB_SERVER_CONF_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(getContext()).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(getContext()).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(getContext(), mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkData(String operation_date, String job_count, String job_time) {
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(getContext(), "请选择中心名称", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(operation_date)) {
            Toast.makeText(getContext(), "请选择任务日期", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(job_count)) {
            Toast.makeText(getContext(), "请输入工作量计数", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time)) {
            Toast.makeText(getContext(), "请选择用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
