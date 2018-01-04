package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ItemsBean;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.bean.SubmitCategoryBean;
import com.healthmudi.bean.VisitContentBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.one.adapter.VisitContentAdapter;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:常规访视
 * Created by tck on 2017/12/10.
 */

public class RegularVisitsActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private TextView mTvSubjectsNumber;
    private TextView mTvPlannedDate;
    private TextView mTvSelectResearchCenter;
    private TextView mTvInitials;
    private TextView mTvWindowDate;
    private TextView mTvActualVisitDate;
    private ImageView mIvVisitDateBlack;
    private CheckBox mCbIsSame;
    private EditText mEtRemark;
    private AutoListView mAutoListView;

    private TimePickerView mTimePickerView;
    private IosDialog mIosDialog;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    private Map<String, String> map = new HashMap<>();

    private String tag = "RegularVisitsActivity";


    @Override
    public int getLayoutId() {
        return R.layout.activity_regular_visits;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mSubjectsBean = (SubjectsListBean.SubjectsBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_BEAN);
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
            map.put("project_id", String.valueOf(mSubjectsBean.getProject_id()));
            map.put("subject_id", String.valueOf(mSubjectsBean.getSubject_id()));
            map.put("subject_visit_id", String.valueOf(mSubjectsPersonalListBean.getSubject_visit_id()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();

        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mTvSelectResearchCenter = (TextView) findViewById(R.id.tv_select_research_center);
        mTvInitials = (TextView) findViewById(R.id.tv_initials);
        mTvWindowDate = (TextView) findViewById(R.id.tv_window_date);
        mTvActualVisitDate = (TextView) findViewById(R.id.tv_actual_visit_date);
        mCbIsSame = (CheckBox) findViewById(R.id.cb_is_same);
        mEtRemark = (EditText) findViewById(R.id.et_remark);

        mAutoListView = (AutoListView) findViewById(R.id.auto_list_view);

        mTvSubjectsNumber = (TextView) findViewById(R.id.tv_subjects_number);
        mTvPlannedDate = (TextView) findViewById(R.id.tv_planned_date);

        initCheckBox();

        initTimePick();
        initDialog();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(this)
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("访试成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_REGULAR_VISITS_SUCCESS));
                        finish();
                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
    }

    private void initCheckBox() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 50, 50);
        //设置CheckBox对象的位置，对应为左、上、右、下
        mCbIsSame.setCompoundDrawables(drawable, null, null, null);
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvActualVisitDate.setText(DateUtils.getTime(date));
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
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null && mSubjectsPersonalListBean != null) {
            mTvTitle.setText(mSubjectsPersonalListBean.getVisit_name());
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mTvPlannedDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsPersonalListBean.getTarget_visit_time())));
            mTvWindowDate.setText(mSubjectsPersonalListBean.getWindow_neg() + "天~" + mSubjectsPersonalListBean.getWindow_pos() + "天");
            if (!ListUtil.isEmpty(mSubjectsPersonalListBean.getVisit_content())) {
                mAutoListView.setAdapter(new VisitContentAdapter(this, mSubjectsPersonalListBean.getVisit_content()));
            }
        }


    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.iv_visit_date_black).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_circular_exclamation_mark://注意事项
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                intent.putExtra(Constant.KEY_INFOMATION, MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS);
                intent.putExtra(Constant.KEY_SUBJECT_ID, map.get("subject_id"));
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.iv_visit_date_black:
                mTimePickerView.show();
                break;
        }
    }

    private void submitData() {
        String actual_visit_time = mTvActualVisitDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();
        String not_finish_flag = "0";
        if (mCbIsSame.isChecked()) {
            not_finish_flag = "1";
        }

        if (TextUtils.isEmpty(actual_visit_time)) {
            Toast.makeText(this, "请选择实际访试日期", Toast.LENGTH_SHORT).show();
            return;
        }
        getVisitContent();

        map.put("actual_visit_time", actual_visit_time);
        map.put("not_finish_flag", not_finish_flag);
        map.put("visit_content", getVisitContent());
        map.put("remark", remark);
        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_VISIT_SUBMIT_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(RegularVisitsActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(RegularVisitsActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(RegularVisitsActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getVisitContent() {
        if (mSubjectsPersonalListBean != null
                && !ListUtil.isEmpty(mSubjectsPersonalListBean.getVisit_content())) {
            List<SubmitCategoryBean> list = new ArrayList<>();
            for (int i = 0; i < mSubjectsPersonalListBean.getVisit_content().size(); i++) {
                VisitContentBean visitContentBean = mSubjectsPersonalListBean.getVisit_content().get(i);
                List<String> mStringList = new ArrayList<>();
                for (int j = 0; j < visitContentBean.getItems().size(); j++) {
                    ItemsBean itemsBean = visitContentBean.getItems().get(j);
                    if (itemsBean.isSelected()) {
                        if (TextUtils.isEmpty(itemsBean.getItem_cn())) {
                            mStringList.add(itemsBean.getItem_en());
                        } else {
                            mStringList.add(itemsBean.getItem_en() + "(" + itemsBean.getItem_cn() + ")");
                        }
                    }
                }
                if (!mStringList.isEmpty()) {
                    SubmitCategoryBean submitCategoryBean = new SubmitCategoryBean(visitContentBean.getCategory(), mStringList);
                    list.add(submitCategoryBean);
                }
            }
            return new Gson().toJson(list);
        }
        return "";
    }

}
