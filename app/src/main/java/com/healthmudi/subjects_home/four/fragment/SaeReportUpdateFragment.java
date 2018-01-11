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
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.SubjectCodeBean;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.dialog.SelectSubjectDilaog;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tck
 * Date: 2018/01/05 10：08
 */

public class SaeReportUpdateFragment extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvSubjectsPeople;
    private TextView mTvOperationDate;
    private TextView mTvJobTime;
    private EditText mEtRemark;


    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;
    private IosDialog mIosDialog;
    private SelectSubjectDilaog mSelectSubjectDilaog;

    private Map<String, String> map = new HashMap<>();

    private List<String> mStringList = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();
    private TreeMap<Integer, SubjectCodeBean> selectString = new TreeMap<>();

    private ProjectListBean mProjectListBean;
    private WorkingHoursListBean mWorkingHoursListBean;

    private String site_id = "";
    private String subjects_id = "";
    private String tag = "SaeReportUpdateFragment";

    public static SaeReportUpdateFragment newInstance(WorkingHoursListBean workingHoursListBean) {
        SaeReportUpdateFragment saeReportUpdateFragment = new SaeReportUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        saeReportUpdateFragment.setArguments(bundle);
        return saeReportUpdateFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {

            mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);

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
    protected int getLayoutId() {
        return R.layout.fragment_sae_report_update;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvSubjectsPeople = (TextView) view.findViewById(R.id.tv_subjects_people);
        mTvOperationDate = (TextView) view.findViewById(R.id.tv_operation_date);
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

            subjects_id = mWorkingHoursListBean.getSubjects_id();
            if (mWorkingHoursListBean.getOperation_date() != 0) {
                mTvOperationDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getOperation_date()));
            }
            if (!TextUtils.isEmpty(mWorkingHoursListBean.getSubjects_name())) {
                if (mWorkingHoursListBean.getSubjects_name().contains(",")) {
                    mTvSubjectsPeople.setText(mWorkingHoursListBean.getSubjects_name().replaceAll(",", "\\\n"));
                } else {
                    mTvSubjectsPeople.setText(mWorkingHoursListBean.getSubjects_name());
                }
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
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_SAE_REPORT_SUCCESS));
                activityFinish();
            }
        });
    }


    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.ll_center_name).setOnClickListener(this);
        view.findViewById(R.id.ll_subjects_people).setOnClickListener(this);
        view.findViewById(R.id.ll_operation_date).setOnClickListener(this);
        view.findViewById(R.id.ll_job_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_center_name:
                if (mSiteBeanList.isEmpty()) {
                    Toast.makeText(getContext(), "暂无研究中心", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mSiteBeanList);
                    mOptionsPickerView.show(mTvCenterName);
                }
                break;
            case R.id.ll_subjects_people:
                if (mSelectSubjectDilaog == null) {
                    mSelectSubjectDilaog = new SelectSubjectDilaog();
                }
                mSelectSubjectDilaog.show(getFragmentManager(), "SelectSubjectDilaog");
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
        }
    }

    private StringBuffer sb = new StringBuffer();
    private StringBuffer sb1 = new StringBuffer();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent<TreeMap<Integer, SubjectCodeBean>> event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_SELECT_SUBJECT_SUCCESS)) {
                if (!selectString.isEmpty()) {
                    selectString.clear();
                }
                selectString.putAll(event.getData());
                for (SubjectCodeBean subjectCodeBean : selectString.values()) {
                    sb.append(subjectCodeBean.getSubject_id()).append(",");
                    sb1.append(subjectCodeBean.getSubject_code())
                            .append(" ( ")
                            .append(subjectCodeBean.getName_py())
                            .append(" ) ")
                            .append("\n");
                }
                subjects_id = sb.delete(sb.length() - 1, sb.length()).toString();
                mTvSubjectsPeople.setText(sb1.toString());
                sb.delete(0, sb.length());
                sb1.delete(0, sb.length());
            }
        }
    }

    public void submitData() {
        String operation_date = mTvOperationDate.getText().toString().trim();
        String job_time = mTvJobTime.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(operation_date, job_time)) return;

        map.put("site_id", site_id);
        map.put("subjects_id", subjects_id);
        map.put("operation_date", operation_date);
        map.put("job_time", job_time);
        map.put("remark", remark);
        if (mWorkingHoursListBean != null) {
            map.put("job_id", String.valueOf(mWorkingHoursListBean.getJob_id()));
        } else {
            map.put("job_id", "0");
        }
        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_JOB_SAE_REP_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
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

    private boolean checkData(String operation_date, String job_time) {
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(getContext(), "请选择中心名称", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(subjects_id)) {
            Toast.makeText(getContext(), "请选择受试者", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(operation_date)) {
            Toast.makeText(getContext(), "请选择SAE上报日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time)) {
            Toast.makeText(getContext(), "请选择用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
