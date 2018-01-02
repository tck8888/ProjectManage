package com.healthmudi.home;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 备忘录查看
 * Created by tck
 * Date: 2018/01/02 10：44
 */
public class MemorandumSeeActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvSelectDate;
    private RadioButton mRbMemorandumState;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;//

    private Map<String, String> map = new HashMap<>();
    private String tag = "MemorandumSeeActivity";
    private String memo_id = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_memorandum_see;
    }

    @Override
    public void initData() {
        super.initData();
        map.put("memo_id", memo_id);
    }

    @Override
    public void initView() {
        super.initView();
        mTvSelectDate = (TextView) findViewById(R.id.tv_select_date);
        mEtRemark = (EditText) findViewById(R.id.et_remark);
        mRbMemorandumState = (RadioButton) findViewById(R.id.rb_memorandum_state);
        initTimePick();
        //initRadioButton();
    }

    private void initRadioButton() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style1);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 36, 36);
        //设置CheckBox对象的位置，对应为左、上、右、下
        mRbMemorandumState.setCompoundDrawables(drawable, null, null, null);
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (v.getId() == R.id.tv_select_date) {
                    mTvSelectDate.setText(DateUtils.getTime(date));
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

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_select_date).setOnClickListener(this);
        findViewById(R.id.fl_delete_memorandum).setOnClickListener(this);
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
            case R.id.ll_select_date:
                mTimePickerView.show(mTvSelectDate);
                break;
            case R.id.fl_delete_memorandum:
                deleData();
                break;
        }
    }

    /**
     * decription:删除日程
     */
    private void deleData() {
        if (map.containsKey("memo_time")
                || map.containsKey("status")
                || map.containsKey("memo_content")) {
            map.remove("memo_time");
            map.remove("status");
            map.remove("memo_content");
        }
        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.SCHEDULE_ADD_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(MemorandumSeeActivity.this).hidden();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_MEMORANDUM_SEE_SUCCESS));
                Toast.makeText(MemorandumSeeActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                activityFinish();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(MemorandumSeeActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(MemorandumSeeActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 更新数据
     */
    private void submitData() {
        String memo_time = mTvSelectDate.getText().toString().trim();
        String memo_content = mEtRemark.getText().toString().trim();
        if (TextUtils.isEmpty(memo_time)) {
            Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(memo_content)) {
            Toast.makeText(this, "请输入备忘录内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String status = "0";
        if (mRbMemorandumState.isChecked()) {
            status = "1";
        }
        map.put("memo_content", memo_content);
        map.put("memo_time", memo_time);
        map.put("status", status);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.SCHEDULE_ADD_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(MemorandumSeeActivity.this).hidden();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_MEMORANDUM_SEE_SUCCESS));
                Toast.makeText(MemorandumSeeActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                activityFinish();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(MemorandumSeeActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(MemorandumSeeActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
