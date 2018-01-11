package com.healthmudi.subjects_home.four;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.WorkTimeSubmissionItemListBean;
import com.healthmudi.bean.WorkingHoursListBean;
import com.healthmudi.subjects_home.four.fragment.ContractFollowUpDetailFragment;
import com.healthmudi.subjects_home.four.fragment.ContractFollowUpUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.EDCFillInDetailFragment;
import com.healthmudi.subjects_home.four.fragment.EDCFillInUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.EthicalSubmissionDetailFragment;
import com.healthmudi.subjects_home.four.fragment.EthicalSubmissionUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.InstitutionEstablishmentDetailFragment;
import com.healthmudi.subjects_home.four.fragment.InstitutionEstablishmentUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.OtherWorkDetailFragment;
import com.healthmudi.subjects_home.four.fragment.OtherWorkUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.PresiftingDetailFragment;
import com.healthmudi.subjects_home.four.fragment.PresiftingUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.ProjectStartMeetingDetailFragment;
import com.healthmudi.subjects_home.four.fragment.ProjectStartMeetingUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.SaeReportDetailFragment;
import com.healthmudi.subjects_home.four.fragment.SaeReportUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.ServerConfDetailFragment;
import com.healthmudi.subjects_home.four.fragment.ServerConfUpdateFragment;
import com.healthmudi.subjects_home.four.fragment.VisitorsVisitToTheRulesDetailFragment;
import com.healthmudi.subjects_home.four.fragment.VisitorsVisitToTheRulesUpdateFragment;
import com.healthmudi.subjects_home.one.PlannedInterviewMattersNeedingAttentionActivity;

/**
 * Created by tck
 * Date: 2017/12/29 11：06
 */

public class WorkHourDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvCheckMark;
    private ImageView mIvArrowLeftBlack;
    private ImageView mIvCircularExclamationMark;

    private WorkingHoursListBean mWorkingHoursListBean;
    private WorkTimeSubmissionItemListBean mWorkTimeSubmissionItemListBean;

    private SaeReportDetailFragment mSaeReportDetailFragment;
    private SaeReportUpdateFragment mSaeReportUpdateFragment;

    private ContractFollowUpDetailFragment mContractFollowUpDetailFragment;
    private ContractFollowUpUpdateFragment mContractFollowUpUpdateFragment;

    private VisitorsVisitToTheRulesDetailFragment mVisitorsVisitToTheRulesDetailFragment;
    private VisitorsVisitToTheRulesUpdateFragment mVisitorsVisitToTheRulesUpdateFragment;

    private ServerConfDetailFragment mServerConfDetailFragment;
    private ServerConfUpdateFragment mServerConfUpdateFragment;

    private ProjectStartMeetingDetailFragment mProjectStartMeetingDetailFragment;
    private ProjectStartMeetingUpdateFragment mProjectStartMeetingUpdateFragment;

    private PresiftingDetailFragment mPresiftingDetailFragment;
    private PresiftingUpdateFragment mPresiftingUpdateFragment;

    private OtherWorkDetailFragment mOtherWorkDetailFragment;
    private OtherWorkUpdateFragment mOtherWorkUpdateFragment;

    private InstitutionEstablishmentDetailFragment mInstitutionEstablishmentDetailFragment;
    private InstitutionEstablishmentUpdateFragment mInstitutionEstablishmentUpdateFragment;

    private EthicalSubmissionDetailFragment mEthicalSubmissionDetailFragment;
    private EthicalSubmissionUpdateFragment mEthicalSubmissionUpdateFragment;

    private EDCFillInDetailFragment mEdcFillInDetailFragment;
    private EDCFillInUpdateFragment mEdcFillInUpdateFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_hour_detail;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mWorkingHoursListBean = (WorkingHoursListBean) getIntent().getSerializableExtra(Constant.KEY_WORKING_HOURS_LIST_BEAN);
            mWorkTimeSubmissionItemListBean = (WorkTimeSubmissionItemListBean) getIntent().getSerializableExtra(Constant.KEY_WORKTIME_SUBMISSION_ITEM_LIST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();

        mIvArrowLeftBlack = (ImageView) findViewById(R.id.iv_arrow_left_black);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvCheckMark = (ImageView) findViewById(R.id.iv_check_mark);
        mIvCircularExclamationMark = (ImageView) findViewById(R.id.iv_circular_exclamation_mark);

    }

    @Override
    public void setViewData() {
        super.setViewData();
        mIvArrowLeftBlack.setImageResource(R.mipmap.arrow_left_black);
        mIvCircularExclamationMark.setVisibility(View.GONE);
        int job_type_id = -1;
        if (mWorkTimeSubmissionItemListBean != null) {
            mTvTitle.setText(mWorkTimeSubmissionItemListBean.getJob_type_name());
            job_type_id=mWorkTimeSubmissionItemListBean.getJob_type_id();
        }
        if (mWorkingHoursListBean != null) {
            mTvTitle.setText(mWorkingHoursListBean.getJob_type_name());
            job_type_id=mWorkingHoursListBean.getJob_type_id();
        }
        mIvCheckMark.setVisibility(View.VISIBLE);
        mIvCheckMark.setOnClickListener(this);

        openUpdateFragment(job_type_id);
    }

    private void openDetailFragment(WorkingHoursListBean workingHoursListBean) {
        switch (workingHoursListBean.getJob_type_id()) {
            case 1:
                mInstitutionEstablishmentDetailFragment = InstitutionEstablishmentDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mInstitutionEstablishmentDetailFragment);
                break;
            case 2:
                mEthicalSubmissionDetailFragment = EthicalSubmissionDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mEthicalSubmissionDetailFragment);
                break;
            case 3:
                mContractFollowUpDetailFragment = ContractFollowUpDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mContractFollowUpDetailFragment);
                break;
            case 4:
                mProjectStartMeetingDetailFragment = ProjectStartMeetingDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mProjectStartMeetingDetailFragment);
                break;
            case 5://sae上报
                mIvCircularExclamationMark.setVisibility(View.VISIBLE);
                mIvCircularExclamationMark.setOnClickListener(this);
                mSaeReportDetailFragment = SaeReportDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mSaeReportDetailFragment);
                break;
            case 6:
                mPresiftingDetailFragment = PresiftingDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mPresiftingDetailFragment);
                break;
            case 7://受试者访规
                mVisitorsVisitToTheRulesDetailFragment = VisitorsVisitToTheRulesDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mVisitorsVisitToTheRulesDetailFragment);
                break;
            case 8:
                mEdcFillInDetailFragment = EDCFillInDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mEdcFillInDetailFragment);
                break;
            case 9:
                mServerConfDetailFragment = ServerConfDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mServerConfDetailFragment);
                break;
            case 99:
                mOtherWorkDetailFragment = OtherWorkDetailFragment.newInstance(mWorkingHoursListBean);
                openFragment(mOtherWorkDetailFragment);
                break;
        }
    }

    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragment)
                .commit();
    }

    private void openUpdateFragment(int job_type_id) {
        switch (job_type_id) {
            case 1://机构立项
                mInstitutionEstablishmentUpdateFragment = InstitutionEstablishmentUpdateFragment.newInstance(mWorkingHoursListBean);
                openFragment(mInstitutionEstablishmentUpdateFragment);
                break;
            case 2://伦理递交
                mEthicalSubmissionUpdateFragment = EthicalSubmissionUpdateFragment.newInstance();
                openFragment(mEthicalSubmissionUpdateFragment);
                break;
            case 3://合同跟进
                mContractFollowUpUpdateFragment = ContractFollowUpUpdateFragment.newInstance(mWorkingHoursListBean);
                openFragment(mContractFollowUpUpdateFragment);
                break;
            case 4:
                mProjectStartMeetingUpdateFragment = ProjectStartMeetingUpdateFragment.newInstance();
                openFragment(mProjectStartMeetingUpdateFragment);
                break;
            case 5://sae上报
                mIvCircularExclamationMark.setVisibility(View.VISIBLE);
                mIvCircularExclamationMark.setOnClickListener(this);
                mSaeReportUpdateFragment = SaeReportUpdateFragment.newInstance();
                openFragment(mSaeReportUpdateFragment);
                break;
            case 6:
                mPresiftingUpdateFragment = PresiftingUpdateFragment.newInstance();
                openFragment(mPresiftingUpdateFragment);
                break;
            case 7:
                mVisitorsVisitToTheRulesUpdateFragment = VisitorsVisitToTheRulesUpdateFragment.newInstance();
                openFragment(mVisitorsVisitToTheRulesUpdateFragment);
                break;
            case 8:
                mEdcFillInUpdateFragment = EDCFillInUpdateFragment.newInstance();
                openFragment(mEdcFillInUpdateFragment);
                break;
            case 9:
                mServerConfUpdateFragment = ServerConfUpdateFragment.newInstance(mWorkTimeSubmissionItemListBean);
                openFragment(mServerConfUpdateFragment);
                break;
            case 99:
                mOtherWorkUpdateFragment = OtherWorkUpdateFragment.newInstance();
                openFragment(mOtherWorkUpdateFragment);
                break;
        }

    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                intent.putExtra(Constant.KEY_INFOMATION, MessageEvent.KEY_SAE_REPORT_SUCCESS);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.iv_check_mark:
                if (mInstitutionEstablishmentUpdateFragment != null) {
                    mInstitutionEstablishmentUpdateFragment.submitData();
                } else if (mSaeReportUpdateFragment != null) {
                    mSaeReportUpdateFragment.submitData();
                } else if (mPresiftingUpdateFragment != null) {
                    mPresiftingUpdateFragment.submitData();
                } else if (mVisitorsVisitToTheRulesUpdateFragment != null) {
                    mVisitorsVisitToTheRulesUpdateFragment.submitData();
                } else if (mEthicalSubmissionUpdateFragment != null) {
                    mEthicalSubmissionUpdateFragment.submitData();
                } else if (mContractFollowUpUpdateFragment != null) {
                    mContractFollowUpUpdateFragment.submitData();
                } else if (mProjectStartMeetingUpdateFragment != null) {
                    mProjectStartMeetingUpdateFragment.submitData();
                } else if (mEdcFillInUpdateFragment != null) {
                    mEdcFillInUpdateFragment.submitData();
                } else if (mOtherWorkUpdateFragment != null) {
                    mOtherWorkUpdateFragment.submitData();
                } else if (mServerConfUpdateFragment != null) {
                    mServerConfUpdateFragment.submitData();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mTvTitle = null;
            mIvCheckMark = null;
            mIvArrowLeftBlack = null;
            mIvCircularExclamationMark = null;
            mWorkingHoursListBean = null;
            mWorkTimeSubmissionItemListBean = null;
            mSaeReportDetailFragment = null;
            mSaeReportUpdateFragment = null;
            mContractFollowUpDetailFragment = null;
            mContractFollowUpUpdateFragment = null;
            mVisitorsVisitToTheRulesDetailFragment = null;
            mVisitorsVisitToTheRulesUpdateFragment = null;
            mServerConfDetailFragment = null;
            mServerConfUpdateFragment = null;
            mProjectStartMeetingDetailFragment = null;
            mProjectStartMeetingUpdateFragment = null;
            mPresiftingDetailFragment = null;
            mPresiftingUpdateFragment = null;
            mOtherWorkDetailFragment = null;
            mOtherWorkUpdateFragment = null;
            mInstitutionEstablishmentDetailFragment = null;
            mInstitutionEstablishmentUpdateFragment = null;
            mEthicalSubmissionDetailFragment = null;
            mEthicalSubmissionUpdateFragment = null;
            mEdcFillInDetailFragment = null;
            mEdcFillInUpdateFragment = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
