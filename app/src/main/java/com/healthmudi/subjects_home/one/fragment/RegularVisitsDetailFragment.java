package com.healthmudi.subjects_home.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.Items2Bean;
import com.healthmudi.bean.ItemsBean;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.bean.VisitContentBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.subjects_home.one.adapter.VisitContentDetailAdapter;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.utils.gson.GsonUtils;

import java.util.List;

/**
 * Created by tck常规访视的详情界面
 * Date: 2018/01/04 14：53
 */

public class RegularVisitsDetailFragment extends BaseFragment1 {

    private TextView mTvSelectResearchCenter;
    private TextView mTvSubjectsNumber;
    private TextView mTvInitials;
    private TextView mTvPlannedDate;
    private TextView mTvWindowDate;
    private TextView mTvActualVisitDate;
    private AutoListView mAutoListView;
    private TextView mTvRemark;
    private View mFlFinishFlag;


    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    public static RegularVisitsDetailFragment newInstance(SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        RegularVisitsDetailFragment regularVisitsDetailFragment = new RegularVisitsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
        bundle.putSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
        regularVisitsDetailFragment.setArguments(bundle);
        return regularVisitsDetailFragment;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mSubjectsBean = (SubjectsListBean.SubjectsBean) arguments.getSerializable(Constant.KEY_SUBJECTS_BEAN);
        mSubjectsPersonalListBean = (SubjectsPersonalListBean) arguments.getSerializable(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_regular_visits_detail;
    }

    @Override
    protected void initView(@Nullable View view) {
        mTvSelectResearchCenter = (TextView) view.findViewById(R.id.tv_select_research_center);
        mTvSubjectsNumber = (TextView) view.findViewById(R.id.tv_subjects_number);
        mTvInitials = (TextView) view.findViewById(R.id.tv_initials);
        mTvPlannedDate = (TextView) view.findViewById(R.id.tv_planned_date);
        mTvWindowDate = (TextView) view.findViewById(R.id.tv_window_date);
        mTvActualVisitDate = (TextView) view.findViewById(R.id.tv_actual_visit_date);
        mAutoListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
        mFlFinishFlag = view.findViewById(R.id.fl_finish_flag);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null && mSubjectsPersonalListBean != null) {
            mTvSelectResearchCenter.setText(mSubjectsBean.getSite_name());
            mTvInitials.setText(mSubjectsBean.getName_py());
            mTvSubjectsNumber.setText(mSubjectsBean.getSubject_code());
            mTvPlannedDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsPersonalListBean.getTarget_visit_time())));
            mTvActualVisitDate.setText(DateUtils.getFormatTime(String.valueOf(mSubjectsPersonalListBean.getActual_visit_time())));
            mTvWindowDate.setText(mSubjectsPersonalListBean.getWindow_neg() + "天 ~ +" + mSubjectsPersonalListBean.getWindow_pos() + "天");
            if (!ListUtil.isEmpty(mSubjectsPersonalListBean.getVisit_content())) {
                updateVisitContent(mSubjectsPersonalListBean);
            }

            if (mSubjectsPersonalListBean.getNot_finish_flag() == 0) {//0完成，1未完成
                mFlFinishFlag.setVisibility(View.GONE);
            } else {
                mFlFinishFlag.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(mSubjectsPersonalListBean.getRemark())) {
                mTvRemark.setVisibility(View.GONE);
            } else {
                mTvRemark.setText(mSubjectsPersonalListBean.getRemark());
            }
        }
    }

    private void updateVisitContent(SubjectsPersonalListBean subjectsPersonalListBean) {
        if (!TextUtils.isEmpty(subjectsPersonalListBean.getActual_visit_content())) {
            List<Items2Bean> itemsBeen = GsonUtils.jsonToArray(subjectsPersonalListBean.getActual_visit_content(), Items2Bean.class);
            if (!ListUtil.isEmpty(itemsBeen)) {
                for (Items2Bean itemsBean : itemsBeen) {
                    for (VisitContentBean visitContentBean : subjectsPersonalListBean.getVisit_content()) {
                        if (itemsBean.getCategory().equals(visitContentBean.getCategory())) {
                            for (String str : itemsBean.getItems()) {
                                for (ItemsBean visitContentItemsBean : visitContentBean.getItems()) {
                                    if (str.equals(visitContentItemsBean.getItem_cn())
                                            || str.equals(visitContentItemsBean.getItem_en())) {
                                        visitContentItemsBean.setSelected(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (VisitContentBean visitContentBean : subjectsPersonalListBean.getVisit_content()) {
            visitContentBean.setSelected(getStatus(visitContentBean));
        }

        mAutoListView.setAdapter(new VisitContentDetailAdapter(getContext(), subjectsPersonalListBean.getVisit_content()));

    }

    private boolean getStatus(VisitContentBean visitContentBean) {
        for (ItemsBean visitContentItemsBean : visitContentBean.getItems()) {
            if (!visitContentItemsBean.isSelected()) {
                return false;
            }
        }
        return true;
    }
}
