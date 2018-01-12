package com.healthmudi.home.home_fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.healthmudi.ExitProjectManagerActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.ItemType;
import com.healthmudi.bean.MemoBean;
import com.healthmudi.bean.ScheduleListBean;
import com.healthmudi.bean.ScheduleListFootBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.home.MemorandumAddActivity;
import com.healthmudi.home.home_fragment.adapter.CalendarAdapter;
import com.healthmudi.home.home_fragment.adapter.CalendarBean;
import com.healthmudi.home.home_fragment.adapter.MemoListAdapter;
import com.healthmudi.home.home_fragment.adapter.ScheduleListAdapter;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment2 extends BaseFragment1 implements View.OnClickListener {


    private TextView mTvCurrentDay;

    private RecyclerView mCalendarRecyclerView;

    private int currentDay;
    private int currentMontOfDays;
    private int currentYear;
    private int currentMonth;
    private int firstDayWeek;
    private int month;
    private int year;

    private List<CalendarBean> mCalendarBeanList = new ArrayList<>();
    private CalendarAdapter mCalendarAdapter;
    private GridLayoutManager mGridLayoutManager;

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


    public static ScheduleFragment2 newInstance() {
        ScheduleFragment2 scheduleFragment = new ScheduleFragment2();

        return scheduleFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule2;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        currentYear = DateUtils.getYear();
        year = DateUtils.getYear();
        currentMonth = DateUtils.getMonth();
        month = DateUtils.getMonth();
        currentDay = DateUtils.getDay();
        currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
        firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);

        setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);
    }

    @Override
    protected void initView(@Nullable View view) {
        map.put("month", "201712");
        getData();

        mTvCurrentDay = (TextView) view.findViewById(R.id.tv_current_day);
        mTvCurrentDay.setText(getDateStr(currentYear, currentMonth));
        mCalendarRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getContext(), 7);
        mCalendarAdapter = new CalendarAdapter(getContext(), mCalendarBeanList);
        mCalendarRecyclerView.setLayoutManager(mGridLayoutManager);
        mCalendarRecyclerView.setAdapter(mCalendarAdapter);

        View headView = View.inflate(getContext(), R.layout.head_view_layout5, null);
        mTvDate = (TextView) headView.findViewById(R.id.tv_date);
        mCbIsSame = (CheckBox) headView.findViewById(R.id.cb_is_same);
        initCheckBox();
        initTabLayouy(headView);

        mExpandableListView = (ExpandableListView) view.findViewById(R.id.recyclerView);
        mExpandableListView.setGroupIndicator(null);
        ///mAdapter = new ScheduleListAdapter(getContext(), mDataList);
        mExpandableListView.addHeaderView(headView);
        mExpandableListView.addHeaderView(headView);
        mExpandableListView.addHeaderView(headView);
        mExpandableListView.addHeaderView(headView);
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

    private float downY;
    private float mLastY;
    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_pre_month).setOnClickListener(this);
        view.findViewById(R.id.iv_next_month).setOnClickListener(this);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);
        view.findViewById(R.id.iv_add_memorandum).setOnClickListener(this);

        mExpandableListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                int action = ev.getAction();
                float y = ev.getY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mLastY = downY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dy = y - mLastY;
                        Log.d("onIntercept", dy + "");

                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
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
            case R.id.iv_pre_month:
                if (currentMonth - 1 == 0) {
                    currentYear = currentYear - 1;
                    currentMonth = 12;
                } else {
                    currentMonth = currentMonth - 1;
                }
                firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);
                currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
                setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);
                mTvCurrentDay.setText(getDateStr(currentYear, currentMonth));
                mCalendarAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_next_month:
                if (currentMonth + 1 > 12) {
                    currentYear = currentYear + 1;
                    currentMonth = 1;
                } else {
                    currentMonth = currentMonth + 1;
                }
                firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);
                currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
                setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);
                mTvCurrentDay.setText(getDateStr(currentYear, currentMonth));
                mCalendarAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void getData() {
        HttpRequest.getInstance().get(ProjectManageHttpUrlList.SCHEDULE_LIST_URL, map, tag, new OnServerCallBack<HttpResult<ScheduleListBean>, ScheduleListBean>() {
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

    private void setData(int currentYear, int currentMonth, int currentMontOfDays, int firstDayWeek) {
        if (!mCalendarBeanList.isEmpty()) {
            mCalendarBeanList.clear();
        }
        if (firstDayWeek != 6) {
            for (int i = 0; i <= firstDayWeek; i++) {
                mCalendarBeanList.add(new CalendarBean(0, false, true, ""));
            }
        }
        for (int i = 1; i <= currentMontOfDays; i++) {
            if (i == currentDay && currentMonth == month && year == currentYear) {
                mCalendarBeanList.add(new CalendarBean(i, true, false, getDateStr(currentYear, currentMonth, i)));
            } else {
                mCalendarBeanList.add(new CalendarBean(i, false, false, getDateStr(currentYear, currentMonth, i)));
            }
        }
    }


    public String getDateStr(int year, int month, int day) {
        if (month < 10) {
            if (day < 10) {
                return year + "年" + "0" + month + "月" + "0" + day + "日";
            } else {
                return year + "年" + "0" + month + "月" + day + "日";
            }
        } else {
            if (day < 10) {
                return year + "年" + month + "月" + "0" + day + "日";
            } else {
                return year + "年" + month + "月" + day + "日";
            }
        }
    }

    public String getDateStr(int year, int month) {
        if (month < 10) {
            return year + "年" + "0" + month + "月";
        } else {
            return year + "年" + month + "月";
        }
    }


}
