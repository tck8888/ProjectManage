package com.healthmudi.subjects_home.three;

import android.view.View;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.SignHistoryListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 签到历史
 * Created by tck
 * Date: 2017/12/16 09：53
 */

public class SignHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;

    private List<SignHistoryListBean> mSignHistoryListBeen = new ArrayList<>();
    private SignHistoryAdapter mAdapter;

    {
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市肿瘤医院", "签到时间：2017/12/06  09:00~17:58"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市中山第一医院", "签到时间：2017/12/05  09:00~18:08"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市长征医院", "签到时间：2017/12/03  09:00~19:02"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市瑞金医院", "签到时间：2017/12/02  09:00~18:08"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市复旦大学附属医学院", "签到时间：2017/11/23  09:00~18:08"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市瑞金医院", "签到时间：2017/11/22  09:00~18:08"));
        mSignHistoryListBeen.add(new SignHistoryListBean("上海市瑞金医院", "签到时间：2017/11/19  09:00~16:08"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_history;
    }

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
        }
    }
}
