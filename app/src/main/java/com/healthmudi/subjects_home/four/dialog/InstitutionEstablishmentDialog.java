package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;

/**
 * Created by tck
 * Date: 2017/12/29 13：10
 */

public class InstitutionEstablishmentDialog extends BaseDialogFragment {



    @Override
    protected void initData(@Nullable Bundle arguments) {
        //mWorkingHoursListBean = (WorkingHoursListBean) arguments.getSerializable(Constant.KEY_WORKING_HOURS_LIST_BEAN);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_institution_establishment_detail;
    }

    @Override
    protected void initView(View view) {


    }

    @Override
    public void setViewData(View view) {
        super.setViewData(view);

    }
}
