package com.healthmudi.subjects_home.four;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.healthmudi.bean.SiteApproveListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
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
    private AutoListView mAutoListView;
    private EditText mEtRemark;
    private TextView mTvWorkHour;

    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;
    private IosDialog mIosDialog;

    private SiteApproveListAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();
    private List<String> mStringList = new ArrayList<>();
    private List<SiteApproveListBean> mSiteApproveListBeen = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();

    private ProjectListBean mProjectListBean;

    private String site_id = "";
    private String tag = "InstitutionEstablishmentActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_institution_establishment;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            String[] strings = getResources().getStringArray(R.array.work_hour_array);
            mStringList.addAll(Arrays.asList(strings));
            String[] strings2 = getResources().getStringArray(R.array.site_approve_array);
            for (int i = 0; i < strings2.length; i++) {
                mSiteApproveListBeen.add(new SiteApproveListBean(i + 1, strings2[i], false));
            }
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
        mTvSubmitDate = (TextView) findViewById(R.id.tv_submit_date);
        mTvApprovedDate = (TextView) findViewById(R.id.tv_approved_date);
        mAutoListView = (AutoListView) findViewById(R.id.auto_list_view);
        mTvWorkHour = (TextView) findViewById(R.id.tv_work_hour);
        mEtRemark = (EditText) findViewById(R.id.et_remark);
        mAdapter = new SiteApproveListAdapter(this, mSiteApproveListBeen);
        mAutoListView.setAdapter(mAdapter);
        initTimePick();
        initWorkHourPick();
        initDialog();
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

    public void initWorkHourPick() {
        mOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (v.getId() == R.id.tv_work_hour) {
                    String s = mStringList.get(options1);
                    mTvWorkHour.setText(s);
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
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_INSTITUTION_ESTABLISHMENT_SUCCESS));
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
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_center_name).setOnClickListener(this);
        findViewById(R.id.ll_submit_date).setOnClickListener(this);
        findViewById(R.id.ll_approved_date).setOnClickListener(this);
        findViewById(R.id.ll_work_hour).setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
            case R.id.ll_submit_date:
                mTimePickerView.show(mTvSubmitDate);
                break;
            case R.id.ll_approved_date:
                mTimePickerView.show(mTvApprovedDate);
                break;
            case R.id.ll_work_hour:
                mOptionsPickerView.setPicker(mStringList);
                mOptionsPickerView.show(mTvWorkHour);
                break;
        }
    }

    private void submitData() {
        String site_submit_date = mTvSubmitDate.getText().toString().trim();
        String site_approve_date = mTvApprovedDate.getText().toString().trim();
        String job_time = mTvWorkHour.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(site_submit_date, site_approve_date, job_time)) return;

        map.put("site_id", site_id);
        map.put("site_submit_date", site_submit_date);
        map.put("site_approve_date", site_approve_date);
        map.put("status", getStatus());
        map.put("job_time", job_time);
        map.put("remark", remark);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_JOB_SITE_APPROVE_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(InstitutionEstablishmentActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(InstitutionEstablishmentActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(InstitutionEstablishmentActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkData(String site_submit_date, String site_approve_date, String job_time) {
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(this, "请选择中心名称", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(site_submit_date)) {
            Toast.makeText(this, "请选择完成递交日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(site_approve_date)) {
            Toast.makeText(this, "请选择审批通过日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(job_time)) {
            Toast.makeText(this, "请选择任务用时", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(getStatus())) {
            Toast.makeText(this, "请选择机构立项当前进度", Toast.LENGTH_SHORT).show();
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
}
