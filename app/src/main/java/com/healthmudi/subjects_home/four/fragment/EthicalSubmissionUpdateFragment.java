package com.healthmudi.subjects_home.four.fragment;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import com.healthmudi.bean.SiteApproveListBean;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.adapter.ProgressListAdapter;
import com.healthmudi.subjects_home.four.dialog.SelectDocDialog;
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

/**
 * Created by tck
 * Date: 2018/01/05 11：06
 */

public class EthicalSubmissionUpdateFragment extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvProjectName;
    private TextView mTvCenterName;
    private TextView mTvDocReceiveDate;
    private TextView mTvPiSubmitDate;
    private TextView mTvPiSignDate;
    private TextView mTvEcSubmitDate;
    private TextView mTvEcApproveDate;
    private TextView mTvDocumentsName;
    private TextView mTvJobTime;
    private TextView mTvJobTime2;

    private EditText mEtRemark;

    private AutoListView mAutoListView;
    private ProgressListAdapter mAdapter;

    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;
    private IosDialog mIosDialog;

    private SelectDocDialog mSelectDocDialog;

    private Map<String, String> map = new HashMap<>();
    private List<String> mStringList = new ArrayList<>();
    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();

    private ProjectListBean mProjectListBean;
    private WorkingHoursListBean mWorkingHoursListBean;

    private String site_id = "";
    private String tag = "EthicalSubmissionUpdateFragment";
    private String mDocumentsName = "";

    public static EthicalSubmissionUpdateFragment newInstance(WorkingHoursListBean workingHoursListBean) {
        EthicalSubmissionUpdateFragment contractFollowUpUpdateFragment = new EthicalSubmissionUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN, workingHoursListBean);
        contractFollowUpUpdateFragment.setArguments(bundle);
        return contractFollowUpUpdateFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {

            int status = -1;
            mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);
            if (mWorkingHoursListBean != null) {
                status = mWorkingHoursListBean.getStatus();
            }

            String[] strings = getResources().getStringArray(R.array.work_hour_array);
            mStringList.addAll(Arrays.asList(strings));

            String[] strings2 = getResources().getStringArray(R.array.ec_submit_array);
            for (int i = 0; i < strings2.length; i++) {
                if (status == (i + 1)) {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], true));
                } else {
                    mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], false));
                }
            }
            mProjectListBean = (ProjectListBean) Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
            map.put("project_id", String.valueOf(mProjectListBean.getProject_id()));
            mSiteBeanList.addAll(mProjectListBean.getSite());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ethical_submission_update;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        mTvCenterName = (TextView) view.findViewById(R.id.tv_center_name);
        mTvDocReceiveDate = (TextView) view.findViewById(R.id.tv_doc_receive_date);
        mTvPiSubmitDate = (TextView) view.findViewById(R.id.tv_pi_submit_date);
        mTvPiSignDate = (TextView) view.findViewById(R.id.tv_pi_sign_date);
        mTvEcSubmitDate = (TextView) view.findViewById(R.id.tv_ec_submit_date);
        mTvEcApproveDate = (TextView) view.findViewById(R.id.tv_ec_approve_date);
        mTvDocumentsName = (TextView) view.findViewById(R.id.tv_documents_name);
        mTvJobTime = (TextView) view.findViewById(R.id.tv_job_time);
        mTvJobTime2 = (TextView) view.findViewById(R.id.tv_job_time2);
        mEtRemark = (EditText) view.findViewById(R.id.et_remark);

        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new ProgressListAdapter(getContext(), mSiteApproveListBeen);
        mAutoListView.setAdapter(mAdapter);

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

            if (mWorkingHoursListBean.getDoc_receive_date() != 0) {
                mTvDocReceiveDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getDoc_receive_date()));
            }
            if (mWorkingHoursListBean.getPi_submit_date() != 0) {
                mTvPiSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getPi_submit_date()));
            }
            if (mWorkingHoursListBean.getPi_sign_date() != 0) {
                mTvPiSignDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getPi_sign_date()));
            }
            if (mWorkingHoursListBean.getEc_submit_date() != 0) {
                mTvEcSubmitDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getEc_submit_date()));
            }
            if (mWorkingHoursListBean.getEc_approve_date() != 0) {
                mTvEcApproveDate.setText(DateUtils.getFormatTime2(mWorkingHoursListBean.getEc_approve_date()));
            }
            mDocumentsName = mWorkingHoursListBean.getDocuments_name();
            if (!TextUtils.isEmpty(mWorkingHoursListBean.getDocuments_name())) {
                if (mWorkingHoursListBean.getDocuments_name().contains(",")) {
                    String documents_name = mWorkingHoursListBean.getDocuments_name();
                    mTvDocumentsName.setText(documents_name.replaceAll(",", "\\\n"));
                } else {
                    mTvDocumentsName.setText(mWorkingHoursListBean.getDocuments_name());
                }
            }
            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime.setText(String.valueOf(mWorkingHoursListBean.getJob_time()));
            }
            if (mWorkingHoursListBean.getJob_time() != 0) {
                mTvJobTime2.setText(String.valueOf(mWorkingHoursListBean.getJob_time2()));
            }

            if (!TextUtils.isEmpty(mWorkingHoursListBean.getRemark())) {
                mEtRemark.setText(mWorkingHoursListBean.getRemark());}
        }
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v.getId() == R.id.tv_doc_receive_date) {
                    mTvDocReceiveDate.setText(DateUtils.getTime(date));
                } else if (v.getId() == R.id.tv_pi_submit_date) {
                    mTvPiSubmitDate.setText(DateUtils.getTime(date));
                } else if (v.getId() == R.id.tv_pi_sign_date) {
                    mTvPiSignDate.setText(DateUtils.getTime(date));
                } else if (v.getId() == R.id.tv_ec_submit_date) {
                    mTvEcSubmitDate.setText(DateUtils.getTime(date));
                } else if (v.getId() == R.id.tv_ec_approve_date) {
                    mTvEcApproveDate.setText(DateUtils.getTime(date));
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
                } else if (v.getId() == R.id.tv_job_time2) {
                    String s = mStringList.get(options1);
                    mTvJobTime2.setText(s);
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
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_ETHICAL_SUBMISSION_SUCCESS));
                activityFinish();
            }
        });

    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.ll_center_name).setOnClickListener(this);
        view.findViewById(R.id.ll_doc_receive_date).setOnClickListener(this);
        view.findViewById(R.id.ll_pi_submit_date).setOnClickListener(this);
        view.findViewById(R.id.ll_pi_sign_date).setOnClickListener(this);
        view.findViewById(R.id.ll_ec_submit_date).setOnClickListener(this);
        view.findViewById(R.id.ll_ec_approve_date).setOnClickListener(this);
        view.findViewById(R.id.ll_documents_name).setOnClickListener(this);
        view.findViewById(R.id.ll_job_time).setOnClickListener(this);
        view.findViewById(R.id.ll_job_time2).setOnClickListener(this);

        mAutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mSiteApproveListBeen.get(position).isSeleted()) {
                    for (SiteApproveListBean siteApproveListBean : mSiteApproveListBeen) {
                        siteApproveListBean.setSeleted(false);
                    }
                    mSiteApproveListBeen.get(position).setSeleted(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
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
            case R.id.ll_doc_receive_date:
                mTimePickerView.show(mTvDocReceiveDate);
                break;
            case R.id.ll_pi_submit_date:
                mTimePickerView.show(mTvPiSubmitDate);
                break;
            case R.id.ll_pi_sign_date:
                mTimePickerView.show(mTvPiSignDate);
                break;
            case R.id.ll_ec_submit_date:
                mTimePickerView.show(mTvEcSubmitDate);
                break;
            case R.id.ll_ec_approve_date:
                mTimePickerView.show(mTvEcApproveDate);
                break;
            case R.id.ll_documents_name:
                if (mSelectDocDialog == null) {
                    mSelectDocDialog = new SelectDocDialog();
                }
                mSelectDocDialog.show(getFragmentManager(), "SelectDocDialog");
                break;
            case R.id.ll_job_time:
                mOptionsPickerView.setPicker(mStringList);
                if (!TextUtils.isEmpty(mTvJobTime.getText().toString().trim())) {
                    int i = mStringList.indexOf(mTvJobTime.getText().toString().trim());
                    mOptionsPickerView.setSelectOptions(i);
                }
                mOptionsPickerView.show(mTvJobTime);
                break;
            case R.id.ll_job_time2:
                mOptionsPickerView.setPicker(mStringList);
                if (!TextUtils.isEmpty(mTvJobTime2.getText().toString().trim())) {
                    int i = mStringList.indexOf(mTvJobTime2.getText().toString().trim());
                    mOptionsPickerView.setSelectOptions(i);
                }
                mOptionsPickerView.show(mTvJobTime2);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_SELECT_DOC_SUCCESS)) {
                mDocumentsName = (String) event.getData();
                String all = mDocumentsName.replaceAll(",", "\\\n");
                mTvDocumentsName.setText(all);
            }
        }
    }

    public void submitData() {
        String doc_receive_date = mTvDocReceiveDate.getText().toString().trim();
        String pi_submit_date = mTvPiSubmitDate.getText().toString().trim();
        String pi_sign_date = mTvPiSignDate.getText().toString().trim();
        String ec_submit_date = mTvEcSubmitDate.getText().toString().trim();
        String ec_approve_date = mTvEcApproveDate.getText().toString().trim();
        String job_time = mTvJobTime.getText().toString().trim();
        String job_time2 = mTvJobTime2.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(doc_receive_date, pi_submit_date, pi_sign_date, ec_submit_date, ec_approve_date, mDocumentsName, job_time, job_time2))
            return;

        if (TextUtils.isEmpty(getStatus())) {
            Toast.makeText(getContext(), "请选择伦理递交进度", Toast.LENGTH_SHORT).show();
            return;
        }

        map.put("site_id", site_id);
        map.put("doc_receive_date", doc_receive_date);
        map.put("pi_submit_date", pi_submit_date);
        map.put("pi_sign_date", pi_sign_date);
        map.put("ec_submit_date", ec_submit_date);
        map.put("ec_approve_date", ec_approve_date);
        map.put("documents_name", mDocumentsName);
        map.put("job_time", job_time);
        map.put("job_time2", job_time2);
        map.put("remark", remark);
        map.put("status", getStatus());
        if (mWorkingHoursListBean != null) {
            map.put("job_id", String.valueOf(mWorkingHoursListBean.getJob_id()));
        } else {
            map.put("job_id", "0");
        }
        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(ProjectManageHttpUrlList.PROJECT_JOB_EC_SUBMIT_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
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

    private boolean checkData(String doc_receive_date, String pi_submit_date, String pi_sign_date, String ec_submit_date, String ec_approve_date, String documents_name, String job_time, String job_time2) {
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(getContext(), "请选择中心名称", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(doc_receive_date)) {
            Toast.makeText(getContext(), "请选择接受文件日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(pi_submit_date)) {
            Toast.makeText(getContext(), "请选择递交PI日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(pi_sign_date)) {
            Toast.makeText(getContext(), "请选择PI签字日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(ec_submit_date)) {
            Toast.makeText(getContext(), "请选择递交EC日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(ec_approve_date)) {
            Toast.makeText(getContext(), "请选择获取批件日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(documents_name)) {
            Toast.makeText(getContext(), "请选择递交材料名称", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time)) {
            Toast.makeText(getContext(), "请选择用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time2)) {
            Toast.makeText(getContext(), "请选择用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    @NonNull
    private String getStatus() {
        String status = "";
        for (SiteApproveListBean siteApproveListBean : mSiteApproveListBeen) {
            if (siteApproveListBean.isSeleted()) {
                status = String.valueOf(siteApproveListBean.getId());
            }
        }
        return status;
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
