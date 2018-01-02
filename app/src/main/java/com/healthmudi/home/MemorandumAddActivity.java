package com.healthmudi.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
 * 备忘录添加
 * Created by tck
 * Date: 2018/01/02 10：44
 */
public class MemorandumAddActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvSelectDate;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;

    private Map<String, String> map = new HashMap<>();
    private String tag = "MemorandumAddActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_memorandum_add;
    }

    @Override
    public void initView() {
        super.initView();
        mTvSelectDate = (TextView) findViewById(R.id.tv_select_date);
        mEtRemark = (EditText) findViewById(R.id.et_remark);
        initTimePick();
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
        }
    }

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
        map.put("memo_content", memo_content);
        map.put("memo_time", memo_time);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.SCHEDULE_ADD_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(MemorandumAddActivity.this).hidden();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_MEMORANDUM_ADD_SUCCESS));
                Toast.makeText(MemorandumAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                activityFinish();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(MemorandumAddActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(MemorandumAddActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
