package com.healthmudi.subjects_home.home_fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private LinearLayout mContanier;
    private View mNormalView;
    private View mPermissionsView;


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

        //检查版本是否大于M,权限校验
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        Constant.PERMISSION_READ_LOCATION_STATE);
            } else {//权限通过

            }
        } else {//不用验证权限

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.PERMISSION_READ_LOCATION_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        } else {

        }
    }


    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_sign_history).setOnClickListener(this);
        //view.findViewById(R.id.fl_select_location).setOnClickListener(this);
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
            case R.id.btn_open_gps:
                break;
        }
    }

    public void openActivity(Class clzz) {
        if (clzz != null) {
            Intent intent = new Intent(getContext(), clzz);
            intent.putExtra(Constant.KEY_PROJECT_ID, mProject_id);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }
}
