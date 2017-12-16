package com.healthmudi.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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
        mInformationListBeen.add(new InformationListBean("付款通知", "您10月的薪资已经付款，银行卡尾号1234，金额为800000", "今天09:10", "1"));
        mInformationListBeen.add(new InformationListBean("访视提醒", "您今天共有3位受试者进行访视，时间窗内延期2人人人人人", "昨天15:23", "2"));
        mInformationListBeen.add(new InformationListBean("系统消息", "系统将于2017年12月22日进行系统升级，大约持续4000000", "12月11日", "0"));
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
        mListView.setAdapter(new InformationListAdapter(getContext(), mInformationListBeen));
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), InformationListActivity.class);
                intent.putExtra("InformationListBean",mInformationListBeen.get(position));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                break;
        }
    }
}
