package com.healthmudi.home.home_fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.healthmudi.ExitProjectManagerActivity;
import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MemoBean;
import com.healthmudi.bean.ScheduleList1Bean;
import com.healthmudi.bean.ScheduleListHeadBean;
import com.healthmudi.bean.VisitsBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.home.MemorandumAddActivity;
import com.healthmudi.home.home_fragment.adapter.CalendarBean;
import com.healthmudi.home.home_fragment.adapter.ScheduleListAdapter;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.ScheduleCalendarView1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 日程
 * Created by tck on 2017/12/8.
 */
public class ScheduleFragment1 extends BaseFragment1 implements View.OnClickListener {

    private TextView mTvCurrentDay;
    private ScheduleCalendarView1 mScheduleCalendarView;

    private ExpandableListView mExpandableListView;
    private TextView mTvDate;
    private CheckBox mCbIsSame;
    private TabLayout mTabLayout;
    private View mFootView;

    private ScheduleListAdapter mAdapter;


    private String tag = "ScheduleFragment";
    private Map<String, String> map = new HashMap<>();

    private boolean isVisitCompleteState = true;//是否未完成状态

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private TabLayout.Tab mTab;
    private TabLayout.Tab mTab1;
    private TabLayout.Tab mTab2;
    private TabLayout.Tab mTab3;
    private TabLayout.Tab mTab4;

    private List<ScheduleListHeadBean> mScheduleListAllData = new ArrayList<>();
    private int mPosition = 1;


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
        currentYear = DateUtils.getYear();
        currentMonth = DateUtils.getMonth();
        currentDay = DateUtils.getDay();


    }

    @Override
    protected void initView(@Nullable View view) {

        getData();

        mTvCurrentDay = (TextView) view.findViewById(R.id.tv_current_day);
        mTvCurrentDay.setText(DateUtils.getDateStr(currentYear, currentMonth));

        mScheduleCalendarView = (ScheduleCalendarView1) view.findViewById(R.id.schedule_calendarview);
        View headView = View.inflate(getContext(), R.layout.head_view_layout5, null);
        mTvDate = (TextView) headView.findViewById(R.id.tv_date);
        mTvDate.setText(DateUtils.getDateStr(currentYear, currentMonth, currentDay));

        mCbIsSame = (CheckBox) headView.findViewById(R.id.cb_is_same);
        initCheckBox();
        initTabLayouy(headView);


        mExpandableListView = (ExpandableListView) view.findViewById(R.id.recyclerView);
        mExpandableListView.setGroupIndicator(null);
        mAdapter = new ScheduleListAdapter(getContext(), mScheduleListAllData);
        mExpandableListView.addHeaderView(headView);

        mExpandableListView.setAdapter(mAdapter);

        mFootView = View.inflate(getContext(), R.layout.loading_empty1, null);

    }

    private void initTabLayouy(View headView) {
        mTabLayout = (TabLayout) headView.findViewById(R.id.tab_layout);
        mTab = mTabLayout.newTab().setText("逾期");
        mTab1 = mTabLayout.newTab().setText("当天");
        mTab2 = mTabLayout.newTab().setText("1天后");
        mTab3 = mTabLayout.newTab().setText("3天后");
        mTab4 = mTabLayout.newTab().setText("7天后");
        mTabLayout.addTab(mTab);
        mTabLayout.addTab(mTab1);
        mTabLayout.addTab(mTab2);
        mTabLayout.addTab(mTab3);
        mTabLayout.addTab(mTab4);
        mTabLayout.getTabAt(1).select();
    }


    private void initCheckBox() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.checkbox_style);
        //设置drawable对象的大小
        drawable.setBounds(0, 0, 40, 40);
        //设置CheckBox对象的位置，对应为左、上、右、下
        mCbIsSame.setCompoundDrawables(null, null, drawable, null);
        mCbIsSame.setChecked(true);
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_my_project_center).setOnClickListener(this);
        view.findViewById(R.id.iv_add_memorandum).setOnClickListener(this);
        view.findViewById(R.id.iv_pre_month).setOnClickListener(this);
        view.findViewById(R.id.iv_next_month).setOnClickListener(this);

        mScheduleCalendarView.setOnDateClickListener(new ScheduleCalendarView1.OnDateClickListener() {
            @Override
            public void onClick(CalendarBean calendarBean) {
                if (!mTvDate.getText().toString().trim().equals(calendarBean.getDateStr())) {//日期不同才进行数据的刷新操作
                    //当前天数
                    currentDay = Integer.parseInt(calendarBean.getDateStr().substring(calendarBean.getDateStr().indexOf("月") + 1, calendarBean.getDateStr().indexOf("日")));
                    //刷新数据源
                    refreshData(1, isVisitCompleteState);
                    mTabLayout.getTabAt(1).select();
                    //mTvCurrentDay.setText(calendarBean.getDateStr().substring(0, calendarBean.getDateStr().indexOf("月") + 1));
                    //任务清单的时间
                    mTvDate.setText(DateUtils.getDateStr(currentYear, currentMonth, currentDay));
                }
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPosition = tab.getPosition();
                refreshData(mPosition, isVisitCompleteState);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCbIsSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVisitCompleteState = isChecked;
                changeData(mPosition, isVisitCompleteState);
            }
        });
    }

    /**
     * 切换数据源
     *
     * @param isChecked true 未完成的任务 false所有的任务
     */
    private void changeData(int position, boolean isChecked) {

        //刷新tab数据
        refreshTab(currentYear, currentMonth, currentDay, isChecked);
        //切换适配器的数据源
        refreshData(position, isChecked);
    }

    /**
     * 刷新数据
     *
     * @param position
     */
    private void refreshData(int position, boolean isChecked) {

        int monthDays = DateUtils.getMonthDays(currentYear, currentMonth);
        if (currentDay >= monthDays) {
            if (monthDays == 30) {
                currentDay = 30;
            } else if (monthDays == 31) {
                currentDay = 31;
            } else if (monthDays == 28) {
                currentDay = 28;
            } else if (monthDays == 29) {
                currentDay = 29;
            }
        }
        if (!ListUtil.isEmpty(mScheduleListAllData)) {
            mScheduleListAllData.clear();
        }

        mScheduleListAllData.addAll(getTypeDataList(currentYear, currentMonth, currentDay, position, isChecked));


        //数据为空显示空界面
        if (ListUtil.isEmpty(mScheduleListAllData)) {
            mExpandableListView.addFooterView(mFootView);
        } else {
            mExpandableListView.removeFooterView(mFootView);
        }
        mAdapter.notifyDataSetChanged();
        mCbIsSame.setChecked(isVisitCompleteState);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pre_month:
                if (currentMonth - 1 == 0) {
                    currentYear = currentYear - 1;
                    currentMonth = 12;
                } else {
                    currentMonth = currentMonth - 1;
                }

                mTvCurrentDay.setText(DateUtils.getDateStr(currentYear, currentMonth));
                mScheduleCalendarView.changePreMonth();
                getData();
                break;
            case R.id.iv_next_month:
                if (currentMonth + 1 > 12) {
                    currentYear = currentYear + 1;
                    currentMonth = 1;
                } else {
                    currentMonth = currentMonth + 1;
                }
                mTvCurrentDay.setText(DateUtils.getDateStr(currentYear, currentMonth));
                mScheduleCalendarView.changeNextMonth();
                getData();
                break;
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
        map.put("month", DateUtils.getDateStr1(currentYear, currentMonth));
        HttpRequest.getInstance().get(HttpUrlList.SCHEDULE_LIST_URL, map, tag, new OnServerCallBack<HttpResult<ScheduleList1Bean>, ScheduleList1Bean>() {
            @Override
            public void onSuccess(ScheduleList1Bean result) {
                initCurrentMonthData();
                if (result != null) {
                    operatingData(result);
                }

            }


            @Override
            public void onFailure(int code, String mesage) {

            }
        });
    }


    private TreeMap<String, Map<Integer, Map<String, List<VisitsBean>>>> currentMonthData = new TreeMap<>();

    private void operatingData(ScheduleList1Bean result) {

        long mTimestamp = result.getTimestamp();

        if (memoToVisitsBean(result.getMemo()) != null) {
            result.getVisit().add(memoToVisitsBean(result.getMemo()));
        }

        Iterator<Map.Entry<String, Map<Integer, Map<String, List<VisitsBean>>>>> iterator = currentMonthData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<Integer, Map<String, List<VisitsBean>>>> next = iterator.next();
            String key = next.getKey();//每一天
            Map<Integer, Map<String, List<VisitsBean>>> value = next.getValue();

            Map<String, List<VisitsBean>> overdue_day_map = value.get(0);
            Map<String, List<VisitsBean>> current_day_map = value.get(1);
            Map<String, List<VisitsBean>> one_day_map = value.get(2);
            Map<String, List<VisitsBean>> three_day_map = value.get(3);
            Map<String, List<VisitsBean>> seven_day_map = value.get(4);

            if (!overdue_day_map.isEmpty()) {
                overdue_day_map.clear();
            }
            if (!current_day_map.isEmpty()) {
                current_day_map.clear();
            }
            if (!one_day_map.isEmpty()) {
                one_day_map.clear();
            }
            if (!three_day_map.isEmpty()) {
                three_day_map.clear();
            }
            if (!seven_day_map.isEmpty()) {
                seven_day_map.clear();
            }

            for (ScheduleList1Bean.VisitBean data : result.getVisit()) {
                if (data != null && !ListUtil.isEmpty(data.getVisits()))
                    for (VisitsBean visits : data.getVisits()) {
                        int window_neg = visits.getWindow_neg();//负数，下限
                        int window_pos = visits.getWindow_pos();//正数，上限
                        long currentTimestamp = DateUtils.getTimestamp(key);//当前时间
                        long target_visit_time = visits.getTarget_visit_time();//目标时间
                        long invalid_time = currentTimestamp - Math.abs(window_neg) * 24 * 3600;//窗口期  过期时间
                        long effective_time = target_visit_time + window_pos * 24 * 3600;//窗口期  有效时间
                        long one_day_time = currentTimestamp + 24 * 3600;//1天以后
                        long three_day_time = currentTimestamp + 4 * 24 * 3600;//3天以后
                        long seven_day_time = currentTimestamp + 8 * 24 * 3600;//7天以后

                        if (!data.isMemo()) {
                            visits.setName_py(data.getName_py());
                            visits.setSubject_code(data.getSubject_code());
                        }

                        if (!(visits.getNot_finish_flag() == 1 || (visits.getActual_visit_time() != 0 && visits.getNot_finish_flag() == 0)) && target_visit_time < currentTimestamp && target_visit_time >= invalid_time) {//逾期
                            if (effective_time >= mTimestamp) {
                                break;
                            }
                            if (overdue_day_map.containsKey(data.getProject_name())) {
                                List<VisitsBean> visitsBeen = overdue_day_map.get(data.getProject_name());
                                visitsBeen.add(visits);
                            } else {
                                List<VisitsBean> list = new ArrayList<>();
                                list.add(visits);
                                overdue_day_map.put(data.getProject_name(), list);
                            }
                        } else if (DateUtils.getFormatTime3(visits.getTarget_visit_time()).equals(key)) {//当天
                            if (current_day_map.containsKey(data.getProject_name())) {
                                List<VisitsBean> visitsBeen = current_day_map.get(data.getProject_name());
                                visitsBeen.add(visits);
                            } else {
                                List<VisitsBean> list = new ArrayList<>();
                                list.add(visits);
                                current_day_map.put(data.getProject_name(), list);
                            }
                        } else if (visits.getTarget_visit_time() <= one_day_time && visits.getTarget_visit_time() > currentTimestamp) {//1天后
                            if (one_day_map.containsKey(data.getProject_name())) {
                                List<VisitsBean> visitsBeen = one_day_map.get(data.getProject_name());
                                visitsBeen.add(visits);
                            } else {
                                List<VisitsBean> list = new ArrayList<>();
                                list.add(visits);
                                one_day_map.put(data.getProject_name(), list);
                            }
                        } else if (target_visit_time < three_day_time && target_visit_time >= (currentTimestamp + 3 * 24 * 3600)) {//3天后
                            if (three_day_map.containsKey(data.getProject_name())) {
                                List<VisitsBean> visitsBeen = three_day_map.get(data.getProject_name());
                                visitsBeen.add(visits);
                            } else {
                                List<VisitsBean> list = new ArrayList<>();
                                list.add(visits);
                                three_day_map.put(data.getProject_name(), list);
                            }
                        } else if (target_visit_time < seven_day_time && target_visit_time >= (currentTimestamp + 7 * 24 * 3600)) {//7天后
                            if (seven_day_map.containsKey(data.getProject_name())) {
                                List<VisitsBean> visitsBeen = seven_day_map.get(data.getProject_name());
                                visitsBeen.add(visits);
                            } else {
                                List<VisitsBean> list = new ArrayList<>();
                                list.add(visits);
                                seven_day_map.put(data.getProject_name(), list);
                            }
                        }
                    }
            }
        }

        changeData(mPosition, isVisitCompleteState);
    }

    /**
     * 将备忘录数据转换为VisitBean
     *
     * @param data
     * @return
     */
    public ScheduleList1Bean.VisitBean memoToVisitsBean(List<MemoBean> data) {
        if (data != null) {
            List<VisitsBean> list = new ArrayList<>();
            ScheduleList1Bean.VisitBean visitBean = new ScheduleList1Bean.VisitBean();
            visitBean.setMemo(true);
            visitBean.setProject_name("自定义备忘录");
            for (MemoBean memoBean : data) {
                VisitsBean visitsBean = new VisitsBean();
                visitsBean.setMemo(true);
                visitsBean.setTarget_visit_time(memoBean.getMemo_time());
                visitsBean.setMemo_content(memoBean.getMemo_content());
                visitsBean.setMemo_id(memoBean.getMemo_id());
                visitsBean.setMemoStatus(memoBean.getStatus());
                list.add(visitsBean);
            }
            visitBean.setVisits(list);
            return visitBean;
        }

        return null;

    }

    /**
     * 设置某个状态的的数量
     *
     * @param year
     * @param month
     * @param day
     * @param isOver
     */
    private void refreshTab(int year, int month, int day, boolean isOver) {
        int i;
        i = refreshTab(year, month, day, 0, isOver);
        if (i != 0) {
            mTab.setText("逾期(" + i + ")");
        } else {
            mTab.setText("逾期");
        }
        i = 0;
        i = refreshTab(year, month, day, 1, isOver);
        if (i != 0) {
            mTab1.setText("当天(" + i + ")");
        } else {
            mTab1.setText("当天");
        }


        i = 0;
        i = refreshTab(year, month, day, 2, isOver);
        if (i != 0) {
            mTab2.setText("1天后(" + i + ")");
        } else {
            mTab2.setText("1天后");
        }

        i = 0;
        i = refreshTab(year, month, day, 3, isOver);
        if (i != 0) {
            mTab3.setText("3天后(" + i + ")");
        } else {
            mTab3.setText("3天后");
        }

        i = 0;
        i = refreshTab(year, month, day, 4, isOver);
        if (i != 0) {
            mTab4.setText("7天后(" + i + ")");
        } else {
            mTab4.setText("7天后");
        }
    }

    /**
     * 计算数据量
     *
     * @param year
     * @param month
     * @param day
     * @param position
     * @param isOver
     * @return
     */
    public int refreshTab(int year, int month, int day, int position, boolean isOver) {
        Map<Integer, Map<String, List<VisitsBean>>> stringMapMap = currentMonthData.get(DateUtils.getDateStr(year, month, day));
        int count = 0;
        if (stringMapMap != null) {
            Map<String, List<VisitsBean>> stringListMap = stringMapMap.get(position);
            Iterator<Map.Entry<String, List<VisitsBean>>> iterator = stringListMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<VisitsBean>> next = iterator.next();
                if (isOver) {//未完成的数量
                    for (VisitsBean visitsBean : next.getValue()) {
                        if (visitsBean.isMemo()) {
                            if (visitsBean.getMemoStatus() == 0) {
                                count++;
                            }
                        } else {
                            if (!CommonUtils.isVisitComplete(visitsBean)) {
                                count++;
                            }
                        }
                    }
                } else {
                    count += next.getValue().size();
                }
            }
        }
        return count;
    }


    /**
     * 获取数据源
     *
     * @param year
     * @param month
     * @param day
     * @param position
     * @return
     */
    public List<ScheduleListHeadBean> getTypeDataList(int year, int month, int day, int position, boolean isOver) {
        List<ScheduleListHeadBean> list = new ArrayList<>();
        List<VisitsBean> visitsBeanList = new ArrayList<>();
        Map<Integer, Map<String, List<VisitsBean>>> stringMapMap = currentMonthData.get(DateUtils.getDateStr(year, month, day));
        Map<String, List<VisitsBean>> stringListMap = stringMapMap.get(position);
        Iterator<Map.Entry<String, List<VisitsBean>>> iterator = stringListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<VisitsBean>> next = iterator.next();
            String key = next.getKey();
            List<VisitsBean> value = next.getValue();
            ScheduleListHeadBean scheduleListHeadBean = new ScheduleListHeadBean();
            scheduleListHeadBean.setProject_name(key);
            if (isOver) {//未完成的数量
                if (!ListUtil.isEmpty(visitsBeanList)) {
                    visitsBeanList.clear();
                }
                for (VisitsBean visitsBean : next.getValue()) {
                    if (visitsBean.isMemo()) {
                        if (visitsBean.getMemoStatus() == 0) {
                            visitsBeanList.add(visitsBean);
                        }
                    } else {
                        if (!CommonUtils.isVisitComplete(visitsBean)) {
                            visitsBeanList.add(visitsBean);
                        }
                    }
                }

                if (!visitsBeanList.isEmpty()) {
                    scheduleListHeadBean.setVisits(visitsBeanList);
                    list.add(scheduleListHeadBean);
                } else {
                    break;
                }
            } else {
                scheduleListHeadBean.setVisits(value);
            }

            list.add(scheduleListHeadBean);
        }
        return list;
    }

    /**
     * decription:初始化每一天的数据
     */
    private void initCurrentMonthData() {
        if (!currentMonthData.isEmpty()) {
            currentMonthData.clear();
        }
        int monthDays = DateUtils.getMonthDays(currentYear, currentMonth);
        for (int i = 1; i <= monthDays; i++) {
            Map<Integer, Map<String, List<VisitsBean>>> everyDayData = new HashMap<>();
            everyDayData.put(0, new HashMap<String, List<VisitsBean>>());
            everyDayData.put(1, new HashMap<String, List<VisitsBean>>());
            everyDayData.put(2, new HashMap<String, List<VisitsBean>>());
            everyDayData.put(3, new HashMap<String, List<VisitsBean>>());
            everyDayData.put(4, new HashMap<String, List<VisitsBean>>());
            currentMonthData.put(DateUtils.getDateStr(currentYear, currentMonth, i), everyDayData);
        }
    }
}
