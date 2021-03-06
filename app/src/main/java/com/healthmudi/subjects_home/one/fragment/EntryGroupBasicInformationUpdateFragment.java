package com.healthmudi.subjects_home.one.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.utils.StringConvertCodeEachUtils;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:
 * Created by tck on 2018/1/4.
 */
public class EntryGroupBasicInformationUpdateFragment extends BaseFragment1 implements View.OnClickListener {

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
    private ImageView mIvTestGroup;
    private LinearLayout mLlTestGroup;
    private Space mSpace;
    private ImageView mIvBaselineType;
    private Space mSpBaselineType;
    private ImageView mIvSelectResearchCenter;
    private Space mSpSelectResearchCenter;
    private LinearLayout mLlSelectResearchCenter;

    private TimePickerView mTimePickerView;
    private IosDialog mIosDialog;
    private IosDialog mIosDialog1;
    private OptionsPickerView mOptionsPickerView;

    private ProjectListBean mProjectListBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;
    private SubjectsListBean.SubjectsBean mSubjectsBean;

    private Map<String, String> map = new HashMap<>();
    private List<ProjectListBean.ArmBean> mArmBeanList = new ArrayList<>();
    private List<ProjectListBean.SiteBean> mSiteBeanList = new ArrayList<>();
    private String mArm_code = "";
    private String site_id = "";
    private String tag = "EntryGroupBasicInformationUpdateFragment";

    public static EntryGroupBasicInformationUpdateFragment newInstance(SubjectsPersonalListBean subjectsPersonalListBean) {
        EntryGroupBasicInformationUpdateFragment entryGroupBasicInformationUpdateFragment = new EntryGroupBasicInformationUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        entryGroupBasicInformationUpdateFragment.setArguments(bundle);
        return entryGroupBasicInformationUpdateFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        try {
            mProjectListBean = (ProjectListBean) Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
            mSubjectsBean = (SubjectsListBean.SubjectsBean) Hawk.get(Constant.KEY_SUBJECTS_BEAN);
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
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
    protected int getLayoutId() {
        return R.layout.fragment_entry_group_basic_information_update;
    }

    @Override
    protected void initView(@Nullable View view) {
        mEtInputNumber = (EditText) view.findViewById(R.id.et_input_number);
        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mEtSubjectsNumber = (EditText) view.findViewById(R.id.et_subjects_number);
        mCbIsSame = (CheckBox) view.findViewById(R.id.cb_is_same);
        mEtInitials = (EditText) view.findViewById(R.id.et_initials);
        mEtMobile = (EditText) view.findViewById(R.id.et_mobile);
        mTvBaselineType = (TextView) view.findViewById(R.id.tv_baseline_type);
        mTvBaselineDate = (TextView) view.findViewById(R.id.tv_baseline_date);
        mTvTestGroup = (TextView) view.findViewById(R.id.tv_test_group);
        mEtRemark = (EditText) view.findViewById(R.id.et_remark);

        mIvTestGroup = (ImageView) view.findViewById(R.id.iv_test_group);
        mLlTestGroup = (LinearLayout) view.findViewById(R.id.ll_test_group);
        mSpace = (Space) view.findViewById(R.id.space);


        mIvSelectResearchCenter = (ImageView) view.findViewById(R.id.iv_select_research_center);
        mSpSelectResearchCenter = (Space) view.findViewById(R.id.sp_select_research_center);
        mLlSelectResearchCenter = (LinearLayout) view.findViewById(R.id.ll_select_research_center);


        initCheckBox();
        initTimePick();

        initTestGroupPick();
        initDialog();
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mProjectListBean != null) {
            if (mProjectListBean.getVisit_by_arm() == 0) {//默认分组
                mSpace.setVisibility(View.VISIBLE);
                mTvTestGroup.setText("默认分组");
                mIvTestGroup.setVisibility(View.GONE);
            } else {
                mIvTestGroup.setVisibility(View.VISIBLE);
                mSpace.setVisibility(View.GONE);
                mLlTestGroup.setOnClickListener(this);
            }
            mLlSelectResearchCenter.setOnClickListener(this);
            mTvBaselineType.setText(StringConvertCodeEachUtils.getString(mProjectListBean.getBaseline_type()));
        }

        if (mSubjectsPersonalListBean != null && mSubjectsBean != null) {
            mIvSelectResearchCenter.setVisibility(View.GONE);
            site_id = String.valueOf(mSubjectsBean.getSite_id());
            mSpSelectResearchCenter.setVisibility(View.VISIBLE);
            mEtInputNumber.setText(mSubjectsBean.getSubject_filter_id());
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mEtSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mEtInitials.setText(mSubjectsBean.getName_py());
            mEtMobile.setText(mSubjectsBean.getMobile());
            mTvBaselineDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsBean.getBaseline_date())));
            if (!TextUtils.isEmpty(mSubjectsBean.getRemark())) {
                mEtRemark.setText(mSubjectsBean.getRemark());
            }

        }

    }

    public void initTimePick() {
        mTimePickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
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
                .setTextColorCenter(getResources().getColor(R.color.color_1abc9c))
                .build();
    }

    public void initTestGroupPick() {
        mOptionsPickerView = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (v.getId() == R.id.tv_test_group) {
                    //试验分组
                    ProjectListBean.ArmBean armBean = mArmBeanList.get(options1);
                    mArm_code = armBean.getArm_code();
                    mTvTestGroup.setText(armBean.getArm_name());
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
                .setTextColorCenter(getResources().getColor(R.color.color_1abc9c))
                .setContentTextSize(16)
                .build();
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(getContext())
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("入组成功")
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
                if (mSubjectsPersonalListBean == null) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_ADD_SUCCESS));
                } else {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_UPDATE_SUCCESS));
                }
                activityFinish();
            }
        });

        mIosDialog1 = new IosDialog.Builder(getContext())
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("是否退出编辑")
                .setPositiveButton("是", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        activityFinish();
                    }
                })
                .setNegativeButton("否", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        mIosDialog1.dismiss();
                    }
                })
                .setNegativeButtonColor(getResources().getColor(R.color.color_464c5b))
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setDialogCanceledOnTouchOutside(true)
                .build();
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
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.ll_baseline_date).setOnClickListener(this);


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
    public void onKeyDownBack() {
        super.onKeyDownBack();
        mIosDialog1.show();
    }

    @Override
    public void onClick(View v) {
        hideSoftKeyBord();
        switch (v.getId()) {
            case R.id.ll_select_research_center:
                if (mSiteBeanList.isEmpty()) {
                    Toast.makeText(getContext(), "暂无研究中心", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mSiteBeanList);
                    mOptionsPickerView.show(mTvSelectResearchCenter);
                }
                break;
            //基线日期
            case R.id.ll_baseline_date:
                mTimePickerView.show();
                break;
            case R.id.ll_test_group:
                if (mArmBeanList.isEmpty()) {
                    Toast.makeText(getContext(), "暂无分组信息", Toast.LENGTH_SHORT).show();
                } else {
                    mOptionsPickerView.setPicker(mArmBeanList);
                    mOptionsPickerView.show(mTvTestGroup);
                }
                break;
        }
    }


    private boolean isCheckDataSuccess(String subject_filter_id, String name_py, String subject_code, String mobile, String baseline_type, String baseline_date) {
        if (TextUtils.isEmpty(subject_filter_id)) {
            Toast.makeText(getContext(), "请输入筛选编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(site_id)) {
            Toast.makeText(getContext(), "请选择研究中心", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(name_py)) {
            Toast.makeText(getContext(), "请输入姓名缩写", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(subject_code)) {
            Toast.makeText(getContext(), "请输入受试者编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getContext(), "请输入联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CommonUtils.checkMobile(mobile)) {
            Toast.makeText(getContext(), "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(baseline_type)) {
            Toast.makeText(getContext(), "请选择基线类型", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(baseline_date)) {
            Toast.makeText(getContext(), "请选择基线日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mProjectListBean.getVisit_by_arm() != 0) {
            if (TextUtils.isEmpty(mArm_code)) {
                Toast.makeText(getContext(), "请选择试验分组", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    public void submitData() {
        String subject_filter_id = mEtInputNumber.getText().toString().trim();
        String subject_code = mEtSubjectsNumber.getText().toString().trim();
        String name_py = mEtInitials.getText().toString().trim();
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

        if (mSubjectsPersonalListBean != null && mSubjectsBean != null) {
            map.put("subject_id", String.valueOf(mSubjectsBean.getSubject_id()));
            map.put("subject_visit_id", String.valueOf(mSubjectsPersonalListBean.getSubject_visit_id()));//TODO:
        } else {
            map.put("subject_id", "0");
            map.put("subject_visit_id", "0");
        }

        map.put("name_py", name_py);
        map.put("mobile", mobile);
        map.put("baseline_type", StringConvertCodeEachUtils.getString(baseline_type));
        map.put("baseline_date", baseline_date);
        if (mProjectListBean.getVisit_by_arm() == 0) {//默认分组
            map.put("arm_code", "");
        } else {
            map.put("arm_code", mArm_code);
        }
        map.put("remark", remark);
        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(ProjectManageHttpUrlList.PROJECT_CLOCK_ENROLL_URL, map, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(getContext()).hidden();
                mSubjectsBean.setRemark(mEtRemark.getText().toString().trim());
                Hawk.put(Constant.KEY_PROJECT_LIST_BEAN, mSubjectsBean);
                mIosDialog.show();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(getContext()).hidden();
                Toast.makeText(getContext(), "入组失败,请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mIosDialog = null;
            OkGo.getInstance().cancelTag(tag);
            map.clear();
            mArmBeanList.clear();
            mSiteBeanList.clear();
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

}
