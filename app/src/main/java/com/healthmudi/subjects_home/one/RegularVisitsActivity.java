package com.healthmudi.subjects_home.one;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.one.adapter.VisitContentAdapter;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;

import java.util.Date;

/**
 * decription:常规访视
 * Created by tck on 2017/12/10.
 */

public class RegularVisitsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvArrowLeftBlack;
    private TextView mTvTitle;
    private ImageView mIvCircularExclamationMark;
    private ImageView mIvCheckMark;
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

    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();

        mIvCircularExclamationMark = (ImageView) findViewById(R.id.iv_circular_exclamation_mark);
        mIvCheckMark = (ImageView) findViewById(R.id.iv_check_mark);
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
                finish();
                // 退出动画
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_circular_exclamation_mark:
                break;
            case R.id.iv_check_mark:
                break;
            case R.id.iv_visit_date_black:
                mTimePickerView.show();
                break;
        }
    }

}
