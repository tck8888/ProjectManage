package com.healthmudi.subjects_home.three;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.PunchClockSelectLocationListBean;
import com.healthmudi.subjects_home.three.adapter.PunchClockSelectLocationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择打卡位置
 * Created by tck
 * Date: 2017/12/16 11：43
 */
public class PunchClockSelectLocationActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;

    private List<PunchClockSelectLocationListBean> mPunchClockSelectLocationLiatBeen = new ArrayList<>();
    private PunchClockSelectLocationAdapter mAdapter;

    {
        mPunchClockSelectLocationLiatBeen.add(new PunchClockSelectLocationListBean("上海市中山医院", "<100m", "上海市上海市徐汇区枫林路180号"));
        mPunchClockSelectLocationLiatBeen.add(new PunchClockSelectLocationListBean("上海市肿瘤医院", "524m", "上海市徐汇区东安路270号"));
        mPunchClockSelectLocationLiatBeen.add(new PunchClockSelectLocationListBean("上海市龙华医院", "1.2km", "上海市宛平南路725号"));
        mPunchClockSelectLocationLiatBeen.add(new PunchClockSelectLocationListBean("复旦大学附属中山医院", "2.3km", "上海市徐汇区枫林路180号"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_punch_clock_select_location;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.iv_check_mark:
                break;
        }
    }
}
