package com.healthmudi.home_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.bean.InformationListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:消息
 * Created by tck on 2017/12/8.
 */

public class InformationFragment extends BaseFragment1 implements View.OnClickListener {

    private List<InformationListBean> mInformationListBeen = new ArrayList<>();
    private ListView mListView;

    public static InformationFragment newInstance() {
        InformationFragment informationFragment = new InformationFragment();

        return informationFragment;
    }
    {
        mInformationListBeen.add(new InformationListBean("","您的10月您的10月您的10月您的10月","今天09:00","系统消息"));
        mInformationListBeen.add(new InformationListBean("","您的10月您的10月您的10月您的10月","今天09:00","访视提醒"));
        mInformationListBeen.add(new InformationListBean("","您的10月您的10月您的10月您的10月","今天09:00","系统消息"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.setAdapter(new InformationListAdapter(getContext(),mInformationListBeen));
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                break;
        }
    }
}
