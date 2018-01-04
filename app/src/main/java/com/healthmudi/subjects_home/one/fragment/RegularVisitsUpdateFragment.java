package com.healthmudi.subjects_home.one.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
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
 * Created by tck 常规访视的详情界面编辑界面
 * Date: 2018/01/04 15：39
 */

public class RegularVisitsUpdateFragment extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvSubjectsNumber;
    private TextView mTvPlannedDate;
    private TextView mTvSelectResearchCenter;
    private TextView mTvInitials;
    private TextView mTvWindowDate;
    private TextView mTvActualVisitDate;
    private CheckBox mCbIsSame;
    private EditText mEtRemark;
    private AutoListView mAutoListView;

    private TimePickerView mTimePickerView;
    private IosDialog mIosDialog;

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    private Map<String, String> map = new HashMap<>();

    private String tag = "RegularVisitsUpdateFragment";

    public static RegularVisitsUpdateFragment newInstance(SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        RegularVisitsUpdateFragment regularVisitsUpdateFragment = new RegularVisitsUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        regularVisitsUpdateFragment.setArguments(bundle);
        return regularVisitsUpdateFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {
            mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.getSerializable(Constant.KEY_SUBJECTS_BEAN);
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
            map.put("project_id", String.valueOf(mSubjectsBean.getProject_id()));
            map.put("subject_id", String.valueOf(mSubjectsBean.getSubject_id()));
            map.put("subject_visit_id", String.valueOf(mSubjectsPersonalListBean.getSubject_visit_id()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_regular_visits_update;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mTvInitials = (TextView) view.findViewById(R.id.tv_initials);
        mTvWindowDate = (TextView) view.findViewById(R.id.tv_window_date);

        mTvActualVisitDate = (TextView) view.findViewById(R.id.tv_actual_visit_date);
        mCbIsSame = (CheckBox) view.findViewById(R.id.cb_is_same);
        mEtRemark = (EditText) view.findViewById(R.id.et_remark);
        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mTvSubjectsNumber = (TextView) view.findViewById(R.id.tv_subjects_number);
        mTvPlannedDate = (TextView) view.findViewById(R.id.tv_planned_date);

        initCheckBox();

        initTimePick();
        initDialog();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(getContext())
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("访试成功")
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
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_REGULAR_VISITS_SUCCESS));
               activityFinish();
            }
        });
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
        mTimePickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
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
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mTvPlannedDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsPersonalListBean.getTarget_visit_time())));
            mTvWindowDate.setText(mSubjectsPersonalListBean.getWindow_neg() + "天~" + mSubjectsPersonalListBean.getWindow_pos() + "天");
            if (!ListUtil.isEmpty(mSubjectsPersonalListBean.getVisit_content())) {
                mAutoListView.setAdapter(new VisitContentAdapter(getContext(), mSubjectsPersonalListBean.getVisit_content()));
            }
        }


    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_visit_date_black).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_visit_date_black:
                mTimePickerView.show();
                break;
        }
    }

    public void submitData() {
        String actual_visit_time = mTvActualVisitDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();
        String not_finish_flag = "0";
        if (mCbIsSame.isChecked()) {
            not_finish_flag = "1";
        }

        if (TextUtils.isEmpty(actual_visit_time)) {
            Toast.makeText(getContext(), "请选择实际访试日期", Toast.LENGTH_SHORT).show();
            return;
        }
        getVisitContent();

        map.put("actual_visit_time", actual_visit_time);
        map.put("not_finish_flag", not_finish_flag);
        map.put("visit_content", getVisitContent());
        map.put("remark", remark);
        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_VISIT_SUBMIT_URL, map, tag, new OnServerCallBack() {
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
