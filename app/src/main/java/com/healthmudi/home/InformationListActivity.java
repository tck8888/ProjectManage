package com.healthmudi.home;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.InformationListBean;

/**
 * 消息列表界面
 * Created by tck
 * Date: 2017/12/16 14：26
 */

public class InformationListActivity extends BaseActivity {

    private TextView tvTitle;
    private ExpandableListView expandableListView;
    private InformationListBean mInformationListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_information_list;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mInformationListBean = (InformationListBean) getIntent().getSerializableExtra("InformationListBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);

    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mInformationListBean != null) {
            tvTitle.setText(mInformationListBean.name);
        }
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
