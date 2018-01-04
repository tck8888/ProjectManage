package com.healthmudi.subjects_home.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.StringConvertCodeEachUtils;

/**
 * decription:
 * Created by tck on 2018/1/4.
 */
public class EntryGroupBasicInformationDetailFragment extends BaseFragment1 {

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

    public static EntryGroupBasicInformationDetailFragment newInstance(SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        EntryGroupBasicInformationDetailFragment entryGroupBasicInformationDetailFragment = new EntryGroupBasicInformationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        entryGroupBasicInformationDetailFragment.setArguments(bundle);
        return entryGroupBasicInformationDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.getSerializable(Constant.KEY_SUBJECTS_BEAN);
        mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entry_group_basic_information_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
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
    public void setViewData() {
        super.setViewData();
        if (mSubjectsPersonalListBean != null && mSubjectsBean != null) {
            mTvInputNumber.setText(mSubjectsBean.getSubject_filter_id());
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvMobile.setText(mSubjectsBean.getMobile());
            mTvBaselineType.setText(StringConvertCodeEachUtils.getString(mSubjectsBean.getBaseline_type()));
            mTvBaselineDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsBean.getBaseline_date())));
            mTvTestGroup.setText(mSubjectsBean.getArm_name());
            if (TextUtils.isEmpty(mSubjectsBean.getRemark())) {
                mTvRemark.setVisibility(View.GONE);
            } else {
                mTvRemark.setText(mSubjectsBean.getRemark());
            }

        }
    }
}
