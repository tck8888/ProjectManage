package com.healthmudi.subjects_home.one.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.utils.StringConvertCodeEachUtils;

/**
 * Created by tck
 * Date: 2017/12/26 10：56
 */

public class GroupBasicInformationDialog extends BaseDialogFragment {

    private TextView mTvInputNumber;
    private TextView mTvSelectResearchCenter;
    private TextView mTvSubjectsNumber;
    private TextView mTvInitials;
    private TextView mTvMobile;
    private TextView mTvBaselineType;
    private TextView mTvBaselineDate;
    private TextView mTvTestGroup;
    private TextView mTvRemark;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;
    private SubjectsListBean.SubjectsBean mSubjectsBean;

    public static GroupBasicInformationDialog newInstance(SubjectsPersonalListBean subjectsPersonalListBean, SubjectsListBean.SubjectsBean subjectsBean) {
        GroupBasicInformationDialog groupBasicInformationDialog = new GroupBasicInformationDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        groupBasicInformationDialog.setArguments(bundle);
        return groupBasicInformationDialog;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.get(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
        mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.get(Constant.KEY_SUBJECTS_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_group_basic_information;
    }

    @Override
    protected void initView(View view) {
        mTvInputNumber = (TextView) view.findViewById(R.id.tv_input_number);
        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mTvSubjectsNumber = (TextView) view.findViewById(R.id.tv_subjects_number);
        mTvInitials = (TextView) view.findViewById(R.id.tv_initials);
        mTvMobile = (TextView) view.findViewById(R.id.tv_mobile);
        mTvBaselineType = (TextView) view.findViewById(R.id.tv_baseline_type);
        mTvBaselineDate = (TextView) view.findViewById(R.id.tv_baseline_date);
        mTvTestGroup = (TextView) view.findViewById(R.id.tv_test_group);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);
        if (mSubjectsPersonalListBean != null && mSubjectsBean != null) {
            mTvInputNumber.setText(mSubjectsBean.getSubject_filter_id());
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvMobile.setText(mSubjectsBean.getMobile());
            mTvBaselineType.setText(StringConvertCodeEachUtils.getString(mSubjectsBean.getBaseline_type()));
            mTvBaselineDate.setText(mSubjectsBean.getBaseline_date());
            mTvTestGroup.setText(mSubjectsBean.getArm_name());
            mTvRemark.setText(mSubjectsPersonalListBean.getRemark());

            if (mSubjectsPersonalListBean.getVisit_type() == 1) {
                tvTitle.setText("入组基本信息");
                ivCircularExclamationMark.setVisibility(View.VISIBLE);
                ivCircularExclamationMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }
}
