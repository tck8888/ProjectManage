package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

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
    private IosDialog mIosDialog;
    private OptionsPickerView mOptionsPickerView;

    private ProjectListBean mProjectListBean;
    private Map<String, String> map = new HashMap<>();
    private List<ProjectListBean.ArmBean> mArmBeanList = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();
    private List<String> mBaselinTypes = new ArrayList<>();
    private String mArm_code = "";
    private String site_id = "";
    private String tag = "EntryGroupBasicInformationActivity";


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mIosDialog = null;

            OkGo.getInstance().cancelTag(tag);
            map.clear();
            mArmBeanList.clear();
            mSiteBeanList.clear();
            mBaselinTypes.clear();
            mProjectListBean = null;
            mTimePickerView = null;
            mOptionsPickerView = null;
            mEtInputNumber = null;
            mTvSelectResearchCenter = null;
            mEtSubjectsNumber = null;
            mCbIsSame = null;
            mEtInitials = null;
            mEtMobile = null;
            mTvBaselineType = null;
            mTvBaselineDate = null;
            mTvTestGroup = null;
            mEtRemark = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                if (!ListUtil.isEmpty(mProjectListBean.getSite())) {
                    mSiteBeanList.addAll(mProjectListBean.getSite());
                }
                map.put("project_id", String.valueOf(mProjectListBean.getProject_id()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        initDialog();

    }

    private void initCheckBox() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 36, 36);
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

        mCbIsSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEditor(isChecked);
            }
        });

        mEtInputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCbIsSame.isChecked()) {
                    mEtSubjectsNumber.setText(mEtInputNumber.getText().toString().trim());
                }
            }
        });
    }

    /**
     * decription:设置是否可编辑状态
     */
    public void setEditor(boolean isChecked) {
        if (isChecked) {
            mEtSubjectsNumber.setFocusable(false);
            mEtSubjectsNumber.setFocusableInTouchMode(false);
            mEtSubjectsNumber.setText(mEtInputNumber.getText().toString().trim());
        } else {
            mEtSubjectsNumber.setFocusableInTouchMode(true);
            mEtSubjectsNumber.setFocusable(true);
            mEtSubjectsNumber.requestFocus();
            mEtSubjectsNumber.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        hideSoftKeyBord();
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                startActivity(intent);
                break;
            //提交数据
            case R.id.iv_check_mark:
                submitData();
                break;
            case R.id.ll_select_research_center:
                if (mArmBeanList.isEmpty()) {
                    Toast.makeText(this, "暂无研究中心", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mSiteBeanList);
                    mOptionsPickerView.show(mTvSelectResearchCenter);
                }
                break;
            case R.id.ll_baseline_type:
                if (mBaselinTypes.isEmpty()) {
                    Toast.makeText(this, "暂无基线类型", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mBaselinTypes);
                    mOptionsPickerView.show(mTvBaselineType);
                }
                break;
            //基线日期
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

    private boolean isCheckDataSuccess(String subject_filter_id, String name_py, String subject_code, String mobile, String baseline_type, String baseline_date) {
        if (TextUtils.isEmpty(subject_filter_id)) {
            Toast.makeText(this, "请输入筛选编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(this, "请选择研究中心", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(name_py)) {
            Toast.makeText(this, "请输入姓名缩写", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(subject_code)) {
            Toast.makeText(this, "请输入受试者编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "请输入联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CommonUtils.checkMobile(mobile)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(baseline_type)) {
            Toast.makeText(this, "请选择基线类型", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(baseline_date)) {
            Toast.makeText(this, "请选择基线日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mArm_code)) {
            Toast.makeText(this, "请选择试验分组", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void submitData() {
        String subject_filter_id = mEtInputNumber.getText().toString().trim();
        String name_py = mTvSelectResearchCenter.getText().toString().trim();
        String subject_code = mEtSubjectsNumber.getText().toString().trim();
        String mobile = mEtMobile.getText().toString().trim();
        String baseline_type = mTvBaselineType.getText().toString().trim();
        String baseline_date = mTvBaselineDate.getText().toString().trim();
        String remark = mEtRemark.getText().toString().trim();

        if (!isCheckDataSuccess(subject_filter_id, name_py, subject_code, mobile, baseline_type, baseline_date)) {
            return;
        }

        map.put("site_id", site_id);
        map.put("subject_filter_id", subject_filter_id);
        map.put("subject_code", subject_code);
        map.put("name_py", name_py);
        map.put("mobile", mobile);
        map.put("baseline_type", getString(baseline_type));
        map.put("baseline_date", baseline_date);
        map.put("arm_code", mArm_code);
        map.put("remark", remark);
        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_CLOCK_ENROLL_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(EntryGroupBasicInformationActivity.this).hidden();
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(EntryGroupBasicInformationActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(EntryGroupBasicInformationActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getString(String type) {
        String str = "";
        switch (type) {
            case "上次实际访视日期":
                str = "1";
                break;
            case "知情同意书签署日期":
                str = "2";
                break;
            case "随机分组日期":
                str = "3";
                break;
            case "首次服药日期":
                str = "4";
                break;
        }
        return str;
    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvBaselineDate.setText(DateUtils.getTime(date));
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
                    //试验分组
                    ProjectListBean.ArmBean armBean = mArmBeanList.get(options1);
                    mArm_code = armBean.getArm_code();
                    mTvTestGroup.setText(armBean.getArm_name());
                } else if (v.getId() == R.id.tv_baseline_type) {
                    //基线类型
                    String s = mBaselinTypes.get(options1);
                    mTvBaselineType.setText(s);
                } else if (v.getId() == R.id.tv_select_research_center) {
                    //研究中心
                    ProjectListBean.SiteBean siteBean = mSiteBeanList.get(options1);
                    site_id = String.valueOf(siteBean.getSite_id());
                    mTvSelectResearchCenter.setText(siteBean.getSite_name());
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
                .setMessage("入组成功")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_SUCCESS));
                        finish();
                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
    }


}
