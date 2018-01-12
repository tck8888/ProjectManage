package com.healthmudi.subjects_home.three;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.PunchClockSelectLocationListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.three.adapter.PunchClockSelectLocationAdapter;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择打卡位置
 * Created by tck
 * Date: 2017/12/16 11：43
 */
public class PunchClockSelectLocationActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private ListView listView;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;

    private List<PunchClockSelectLocationListBean> mPunchClockSelectLocationLiatBeen = new ArrayList<>();
    private PunchClockSelectLocationAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();
    private String mProject_id;
    private String tag = "PunchClockSelectLocationActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_punch_clock_select_location;
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
        View headView = View.inflate(this, R.layout.head_view_layout, null);
        listView.addHeaderView(headView);
        mAdapter = new PunchClockSelectLocationAdapter(this, mPunchClockSelectLocationLiatBeen);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_check_mark).setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mPunchClockSelectLocationLiatBeen.get(position - 1).isSelected()) {
                    for (PunchClockSelectLocationListBean punchClockSelectLocationListBean : mPunchClockSelectLocationLiatBeen) {
                        punchClockSelectLocationListBean.setSelected(false);
                    }
                    mPunchClockSelectLocationLiatBeen.get(position - 1).setSelected(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(ProjectManageHttpUrlList.PROJECT_CLOCK_IN_NEARBY_SITE_URL, map, tag, new OnServerCallBack<HttpResult<List<PunchClockSelectLocationListBean>>, List<PunchClockSelectLocationListBean>>() {
            @Override
            public void onSuccess(List<PunchClockSelectLocationListBean> result) {
                if (!ListUtil.isEmpty(mPunchClockSelectLocationLiatBeen)) {
                    mPunchClockSelectLocationLiatBeen.clear();
                }
                mPunchClockSelectLocationLiatBeen.addAll(result);
                if (ListUtil.isEmpty(mPunchClockSelectLocationLiatBeen)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                    mPunchClockSelectLocationLiatBeen.get(0).setSelected(true);
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mPunchClockSelectLocationLiatBeen)) {
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
                activityFinish();
                break;
            case R.id.iv_check_mark:
                selectLocation();
                break;
        }
    }

    private void selectLocation() {
        PunchClockSelectLocationListBean punchClockSelectLocationListBean = getPunchClockSelectLocationListBean();
        if (punchClockSelectLocationListBean != null) {
            EventBus.getDefault().post(new MessageEvent<PunchClockSelectLocationListBean>(MessageEvent.KEY_PUNCH_CLOCK_SELECT_LOCATION_SUCCESS, punchClockSelectLocationListBean));
            activityFinish();
        } else {
            Toast.makeText(this, "前选择打卡位置", Toast.LENGTH_SHORT).show();
        }

    }

    public PunchClockSelectLocationListBean getPunchClockSelectLocationListBean() {
        for (PunchClockSelectLocationListBean punchClockSelectLocationListBean : mPunchClockSelectLocationLiatBeen) {
            if (punchClockSelectLocationListBean.isSelected()) {
                return punchClockSelectLocationListBean;
            }
        }
        return null;
    }
}
