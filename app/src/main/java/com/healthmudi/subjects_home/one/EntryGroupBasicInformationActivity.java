package com.healthmudi.subjects_home.one;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.ListUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:入组基本信息
 * Created by tck on 2017/12/10.
 */

public class EntryGroupBasicInformationActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtInputNumber;
    private TextView mTvSelectResearchCenter;
    private EditText mEtSubjectsNumber;
    private CheckBox mCbIsSame;
    private EditText mEtInitials;
    private EditText mEtMobile;
    private TextView mTvBaselineType;
    private TextView mTvBaselineDate;
    private TextView mTvTestGroup;
    private EditText mEtRemark;

    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;

    private ProjectListBean mProjectListBean;
    private Map<String, String> map = new HashMap<>();
    private List<ProjectListBean.ArmBean> mArmBeanList = new ArrayList<>();
    private List<String> mBaselinTypes = new ArrayList<>();
    private String mArm_code = "";
    private String site_id = "";
    private String tag = "EntryGroupBasicInformationActivity";

    @Override
    public int getLayoutId() {
        return R.layout.entry_group_basic_information;
    }

    @Override
    public void initData() {
        super.initData();
        String[] stringArray = getResources().getStringArray(R.array.base_line_type);
        mBaselinTypes.addAll(Arrays.asList(stringArray));
        try {
            mProjectListBean = (ProjectListBean) getIntent().getSerializableExtra(Constant.KEY_PROJECT_LIST_BEAN);
            if (mProjectListBean != null) {
                if (!ListUtil.isEmpty(mProjectListBean.getArm())) {
                    mArmBeanList.addAll(mProjectListBean.getArm());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("project_id", String.valueOf(mProjectListBean.getProject_id()));


    }

    @Override
    public void initView() {
        super.initView();

        mEtInputNumber = (EditText) findViewById(R.id.et_input_number);
        mTvSelectResearchCenter = (TextView) findViewById(R.id.tv_select_research_center);
        mEtSubjectsNumber = (EditText) findViewById(R.id.et_subjects_number);
        mCbIsSame = (CheckBox) findViewById(R.id.cb_is_same);
        mEtInitials = (EditText) findViewById(R.id.et_initials);
        mEtMobile = (EditText) findViewById(R.id.et_mobile);
        mTvBaselineType = (TextView) findViewById(R.id.tv_baseline_type);
        mTvBaselineDate = (TextView) findViewById(R.id.tv_baseline_date);
        mTvTestGroup = (TextView) findViewById(R.id.tv_test_group);
        mEtRemark = (EditText) findViewById(R.id.et_remark);

        initCheckBox();
        initTimePick();

        initTestGroupPick();
    }

    private void initCheckBox() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 40, 40);
        //设置CheckBox对象的位置，对应为左、上、右、下
        mCbIsSame.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);
        findViewById(R.id.ll_select_research_center).setOnClickListener(this);
        findViewById(R.id.ll_baseline_type).setOnClickListener(this);
        findViewById(R.id.ll_baseline_date).setOnClickListener(this);
        findViewById(R.id.ll_test_group).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.iv_circular_exclamation_mark:
                break;
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_select_research_center:
                break;
            case R.id.ll_baseline_type:
                if (mBaselinTypes.isEmpty()) {
                    Toast.makeText(this, "暂无基线类型", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mBaselinTypes);
                    mOptionsPickerView.show(mTvBaselineType);
                }
                break;
            case R.id.ll_baseline_date:
                mTimePickerView.show();
                break;
            case R.id.ll_test_group:
                if (mArmBeanList.isEmpty()) {
                    Toast.makeText(this, "暂无分组信息", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mArmBeanList);
                    mOptionsPickerView.show(mTvTestGroup);
                }
                break;
        }
    }

    private boolean checkData(String subject_filter_id, String name_py, String subject_code, String mobile, String baseline_type, String baseline_date) {
        if (TextUtils.isEmpty(subject_filter_id)) {
            Toast.makeText(this, "请输入筛选编号", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(this, "请选择研究中心", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(name_py)) {
            Toast.makeText(this, "请输入姓名缩写", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(subject_code)) {
            Toast.makeText(this, "请输入受试者编号", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "请输入联系方式", Toast.LENGTH_SHORT).show();
            return true;
        } if (CommonUtils.checkMobile(mobile)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(baseline_type)) {
            Toast.makeText(this, "请选择基线类型", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(baseline_date)) {
            Toast.makeText(this, "请选择基线日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(mArm_code)) {
            Toast.makeText(this, "请选择试验分组", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private void submitData() {
        String subject_filter_id = mEtInputNumber.getText().toString().trim();
        String name_py = mTvSelectResearchCenter.getText().toString().trim();
        String subject_code = mEtSubjectsNumber.getText().toString().trim();
        String mobile = mEtMobile.getText().toString().trim();
        String baseline_type = mTvBaselineType.getText().toString().trim();
        String baseline_date = mTvBaselineDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (checkData(subject_filter_id, name_py, subject_code, mobile, baseline_type, baseline_date))
            return;
        map.put("site_id", site_id);
        map.put("subject_filter_id", subject_filter_id);
        map.put("subject_code", subject_code);
        map.put("name_py", name_py);
        map.put("mobile", mobile);
        map.put("baseline_type", baseline_type);
        map.put("baseline_date", baseline_date);
        map.put("arm_code", mArm_code);
        map.put("remark", remark);

        HttpRequest.getInstance().post(HttpUrlList.PROJECT_CLOCK_ENROLL_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(int code, String mesage) {

            }
        });
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvBaselineDate.setText(getTime(date));
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

    public void initTestGroupPick() {
        mOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (v.getId() == R.id.tv_test_group) {
                    ProjectListBean.ArmBean armBean = mArmBeanList.get(options1);
                    mArm_code = armBean.getArm_code();
                    mTvTestGroup.setText(armBean.getArm_name());
                } else if (v.getId() == R.id.tv_baseline_type) {
                    String s = mBaselinTypes.get(options1);
                    mTvBaselineType.setText(s);
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


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
