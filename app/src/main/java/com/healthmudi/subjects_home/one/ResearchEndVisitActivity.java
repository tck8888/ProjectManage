package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ResearchEndReasonBean;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.one.adapter.ResearchEndReasonListAdapter;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:研究结束访视
 * Created by tck on 2017/12/10.
 */

public class ResearchEndVisitActivity extends BaseActivity implements View.OnClickListener {


    private TextView mTvSelectResearchCenter;
    private TextView mTvInputNumber;
    private TextView mTvInitials;
    private AutoListView mAutoListView;
    private TextView mTvEndTime;
    private EditText mEtRemark;
    private EditText mEtOtherReason;

    private TimePickerView mTimePickerView;
    private IosDialog mIosDialog;

    private Map<String, String> map = new HashMap<>();
    private List<ResearchEndReasonBean> mResearchEndReasonBeanList = new ArrayList<>();
    private String tag = "ResearchEndVisitActivity";
    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private ResearchEndReasonListAdapter mAdapter;
    private String mReason = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_research_end_visit;
    }

    @Override
    public void initData() {
        super.initData();

        String[] stringArray = getResources().getStringArray(R.array.research_end_reason);
        for (int i = 0; i < stringArray.length; i++) {
            if (i == 0) {
                mResearchEndReasonBeanList.add(new ResearchEndReasonBean(i + 1, stringArray[i], true));
            } else {
                mResearchEndReasonBeanList.add(new ResearchEndReasonBean(i + 1, stringArray[i], false));
            }
        }

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
        mAutoListView = (AutoListView) findViewById(R.id.auto_list_view);
        mEtOtherReason = (EditText) findViewById(R.id.et_other_reason);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mEtRemark = (EditText) findViewById(R.id.et_remark);

        mAdapter = new ResearchEndReasonListAdapter(this, mResearchEndReasonBeanList);
        mAutoListView.setAdapter(mAdapter);

        //设置其他不可编辑
        setEditor(false);
    }

    /**
     * decription:设置是否可编辑状态
     */
    public void setEditor(boolean isChecked) {
        if (isChecked) {
            mReason = mEtOtherReason.getText().toString().trim();
            mEtOtherReason.setFocusableInTouchMode(true);
            mEtOtherReason.setFocusable(true);
            mEtOtherReason.requestFocus();
            mEtOtherReason.setText(mReason);
        } else {
            mEtOtherReason.setFocusable(false);
            mEtOtherReason.setFocusableInTouchMode(false);
            mEtOtherReason.setText("");
            mReason = "";
        }
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
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvEndTime.setText(DateUtils.getTime(date));
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

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(this)
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("研究结束信息提交成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_RESEARCH_END_VISIT_SUCCESS));
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
        findViewById(R.id.ll_end_time).setOnClickListener(this);

        mAutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mResearchEndReasonBeanList.get(position).isCheck()) {
                    for (ResearchEndReasonBean researchEndReasonBean : mResearchEndReasonBeanList) {
                        researchEndReasonBean.setCheck(false);
                    }
                    mResearchEndReasonBeanList.get(position).setCheck(true);
                    mAdapter.notifyDataSetChanged();
                }
                if (mResearchEndReasonBeanList.get(position).getId() == 7 && mResearchEndReasonBeanList.get(position).isCheck()) {
                    setEditor(true);
                } else {
                    setEditor(false);
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
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_end_time:
                mTimePickerView.show();
                break;
        }
    }

    private void submitData() {

        String end_date = mTvEndTime.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (TextUtils.isEmpty(end_date)) {
            Toast.makeText(this, "请选择结束日期", Toast.LENGTH_SHORT).show();
            return;
        }
        int end_reason = 1;
        for (ResearchEndReasonBean researchEndReasonBean : mResearchEndReasonBeanList) {
            if (researchEndReasonBean.isCheck()) {
                end_reason = researchEndReasonBean.getId();
            }
        }
        if (end_reason == 7) {
            if (TextUtils.isEmpty(mReason)) {
                Toast.makeText(this, "请输入原因", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        map.put("end_date", end_date);
        map.put("end_reason", String.valueOf(end_reason));

        if (!TextUtils.isEmpty(mReason)) {
            map.put("end_reason_description", mReason);
        }
        map.put("remark", remark);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_VISIT_END_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(ResearchEndVisitActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(ResearchEndVisitActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(ResearchEndVisitActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
