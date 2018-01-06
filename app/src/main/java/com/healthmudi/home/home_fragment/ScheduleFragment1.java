package com.healthmudi.home.home_fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.healthmudi.ExitProjectManagerActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ItemType;
import com.healthmudi.bean.MemoBean;
import com.healthmudi.bean.ScheduleListBean;
import com.healthmudi.bean.ScheduleListFootBean;
import com.healthmudi.bean.ScheduleListVisitBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.home.MemorandumAddActivity;
import com.healthmudi.home.home_fragment.adapter.CalendarBean;
import com.healthmudi.home.home_fragment.adapter.MemoListAdapter;
import com.healthmudi.home.home_fragment.adapter.ScheduleListAdapter;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment1 extends BaseFragment1 implements View.OnClickListener {

    private RecyclerView mCalendarRecyclerView;
    private TextView mTvCurrentDay;

    private ExpandableListView mExpandableListView;
    private TextView mTvDate;
    private CheckBox mCbIsSame;
    private TabLayout mTabLayout;
    private AutoListView mListView;//

    private ScheduleListAdapter mAdapter;
    private MemoListAdapter mMemoListAdapter;

    private String tag = "ScheduleFragment";
    private Map<String, String> map = new HashMap<>();
    private List<ItemType> mDataList = new ArrayList<>();
    private List<MemoBean> mMemoBeanList = new ArrayList<>();

    private List<CalendarBean> mCalendarBeanList = new ArrayList<>();

    public static ScheduleFragment1 newInstance() {
        ScheduleFragment1 scheduleFragment = new ScheduleFragment1();

        return scheduleFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule1;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected void initView(@Nullable View view) {
        map.put("month", "201712");
        getData();

        View headView = View.inflate(getContext(), R.layout.head_view_layout5, null);
        mTvDate = (TextView) headView.findViewById(R.id.tv_date);
        mCbIsSame = (CheckBox) headView.findViewById(R.id.cb_is_same);
        initCheckBox();
        initTabLayouy(headView);

        View footView = View.inflate(getContext(), R.layout.foot_view_layout, null);
        mListView = (AutoListView) footView.findViewById(R.id.auto_list_view);

        mMemoListAdapter = new MemoListAdapter(getContext(), mMemoBeanList);
        mListView.setAdapter(mMemoListAdapter);

        mExpandableListView = (ExpandableListView) view.findViewById(R.id.recyclerView);
        mExpandableListView.setGroupIndicator(null);
        ///mAdapter = new ScheduleListAdapter(getContext(), mDataList);
        mExpandableListView.addHeaderView(headView);
        //mExpandableListView.addFooterView(footView);

        mExpandableListView.setAdapter(mAdapter);
    }

    private void initTabLayouy(View headView) {
        mTabLayout = (TabLayout) headView.findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("逾期(1)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("当天(4)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("1天后(1)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("3天后(4)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("7天后(2)"));
    }

    private void initCheckBox() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 40, 40);
        //设置CheckBox对象的位置，对应为左、上、右、下
        mCbIsSame.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);
        view.findViewById(R.id.iv_add_memorandum).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_project_center:
                startActivity(new Intent(getContext(), ExitProjectManagerActivity.class));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.iv_add_memorandum:
                startActivity(new Intent(getContext(), MemorandumAddActivity.class));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    public void getData() {
        HttpRequest.getInstance().get(HttpUrlList.SCHEDULE_LIST_URL, map, tag, new OnServerCallBack<HttpResult<ScheduleListBean>, ScheduleListBean>() {
            @Override
            public void onSuccess(ScheduleListBean result) {
                if (!ListUtil.isEmpty(mDataList)) {
                    mDataList.clear();
                }
                if (!ListUtil.isEmpty(result.getVisit())) {
                    mDataList.addAll(result.getVisit());
                }
                //operatingData(result.getVisit());
                if (!ListUtil.isEmpty(result.getMemo())) {
                    ScheduleListFootBean scheduleListFootBean = new ScheduleListFootBean("备忘录", result.getMemo());
                    mDataList.add(scheduleListFootBean);
                }
                mMemoListAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int code, String mesage) {

            }
        });
    }

    private TreeMap<Long, ScheduleListVisitBean> scheduleMap = new TreeMap<>();

   /* private void operatingData(List<ScheduleListBean.VisitBean> visit) {
        for (int i = 0; i < visit.size(); i++) {
            ScheduleListBean.VisitBean visitBean = visit.get(i);
            List<SubjectsBean> subjects = visitBean.getSubjects();

            ScheduleListVisitBean scheduleListVisitBean = new ScheduleListVisitBean();
            scheduleListVisitBean.setProject_name(visitBean.getProject_name());

            List<ScheduleListVisitBean.ScheduleSubject> mScheduleSubjectList = new ArrayList<>();
            for (int j = 0; j < subjects.size(); j++) {
                SubjectsBean subjectsBean = subjects.get(j);
                for (int k = 0; k < subjectsBean.getVisits().size(); k++) {
                    VisitsBean visitsBean = subjectsBean.getVisits().get(k);
                    ScheduleListVisitBean.ScheduleSubject scheduleSubject = new ScheduleListVisitBean.ScheduleSubject();
                    if (visitsBean.getTarget_visit_time() != 0 || visitsBean.getNot_finish_flag() == 1) {

                    }
                }
            }
        }
    }*/


}
