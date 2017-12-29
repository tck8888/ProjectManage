package com.healthmudi.subjects_home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.subjects_home.three.PunchClockSelectLocationActivity;
import com.healthmudi.subjects_home.three.SignHistoryActivity;

/**
 * decription:签到
 * Created by tck on 2017/12/9.
 */

public class SignFragment extends BaseFragment1 implements View.OnClickListener {

    private String mProject_id;

    public static SignFragment newInstance(String project_id) {
        SignFragment signFragment = new SignFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PROJECT_ID, project_id);
        signFragment.setArguments(bundle);
        return signFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mProject_id = arguments.getString(Constant.KEY_PROJECT_ID);
    }

    @Override
    protected void initView(@Nullable View view) {

    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_sign_history).setOnClickListener(this);
        view.findViewById(R.id.fl_select_location).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sign_history:
                openActivity(SignHistoryActivity.class);
                break;
            case R.id.fl_select_location:
                openActivity(PunchClockSelectLocationActivity.class);
                break;
        }
    }

    public void openActivity(Class clzz){
        if (clzz!=null) {
            Intent intent = new Intent(getContext(), clzz);
            intent.putExtra(Constant.KEY_PROJECT_ID,mProject_id);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }
}
