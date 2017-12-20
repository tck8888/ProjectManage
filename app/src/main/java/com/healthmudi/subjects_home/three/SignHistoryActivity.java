package com.healthmudi.subjects_home.three;

import android.view.View;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.SignHistoryListBean;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到历史
 * Created by tck
 * Date: 2017/12/16 09：53
 */

public class SignHistoryActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private ListView listView;
    private EmptyView mEmptyLayout;
    private SmartRefreshLayout mRefreshLayout;

    private SignHistoryAdapter mAdapter;

    private List<SignHistoryListBean> mSignHistoryListBeen = new ArrayList<>();
    private Map<String,String> map = new HashMap<>();
    private String mProject_id;
    private String tag = "SignHistoryActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_history;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mProject_id = getIntent().getStringExtra(Constant.KEY_PROJECT_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("project_id", mProject_id);
    }

    @Override
    public void initView() {
        super.initView();
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) findViewById(R.id.empty_layout);
        listView = (ListView) findViewById(R.id.list_view);
        //为了达到UI的效果设计的一些空白界面
        View headView = View.inflate(this, R.layout.head_view_layout3, null);
        View footView = View.inflate(this, R.layout.head_view_layout4, null);
        listView.addHeaderView(headView);
        listView.addFooterView(footView);
        mAdapter = new SignHistoryAdapter(this, mSignHistoryListBeen);
        listView.setAdapter(mAdapter);
    }


    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_CLOCK_IN_HISTORY_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {
                if (ListUtil.isEmpty(mSignHistoryListBeen)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }

                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mSignHistoryListBeen)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }

                mRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
        }
    }

}
