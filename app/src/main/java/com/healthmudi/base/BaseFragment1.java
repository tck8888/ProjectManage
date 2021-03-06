package com.healthmudi.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.healthmudi.R;

/**
 * Created by tck
 * Date: 2017/12/04 17：26
 */

public abstract class BaseFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setListener(view);
        setViewData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        initData(arguments);

    }

    protected abstract void initData(@Nullable Bundle arguments);

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView(@Nullable View view);

    public void setListener(@Nullable View view) {

    }

    public void setViewData() {

    }


    public void activityFinish() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        ;
    }

    public void hideSoftKeyBord() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    public void onKeyDownBack() {

    }
}
