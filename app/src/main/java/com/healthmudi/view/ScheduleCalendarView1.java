
package com.healthmudi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.home.home_fragment.adapter.CalendarAdapter;
import com.healthmudi.home.home_fragment.adapter.CalendarBean;
import com.healthmudi.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/16 17：14
 */

public class ScheduleCalendarView1 extends FrameLayout implements View.OnClickListener {

    private ImageView mIvPreMonth;
    private TextView mTvCurrentDay;
    private ImageView mIvNextMonth;
    private RecyclerView mCalendarRecyclerView;

    private int currentDay;
    private int currentMontOfDays;
    private int currentYear;
    private int currentMonth;
    private int firstDayWeek;
    private int month;
    private int year;

    private List<CalendarBean> mDataList = new ArrayList<>();
    private CalendarAdapter mCalendarAdapter;
    private GridLayoutManager mGridLayoutManager;

    private int mContentViewId;
    /**
     * ContentView
     */
    ViewGroup mContentView;

    public ScheduleCalendarView1(Context context) {
        this(context, null);
    }

    public ScheduleCalendarView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleCalendarView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScheduleCalendar);
        mContentViewId = array.getResourceId(R.styleable.ScheduleCalendar_schedule_content_view_id, 0);
        array.recycle();

        currentYear = DateUtils.getYear();
        year = DateUtils.getYear();
        currentMonth = DateUtils.getMonth();
        month = DateUtils.getMonth();
        currentDay = DateUtils.getDay();
        currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
        firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);
        setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);

        mGridLayoutManager = new GridLayoutManager(getContext(), 7);
        mCalendarAdapter = new CalendarAdapter(getContext(), mDataList);

        View view = View.inflate(getContext(), R.layout.view_schedule_calendar_layout, null);
        addView(view);
        mIvPreMonth = (ImageView) findViewById(R.id.iv_pre_month);
        mTvCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mIvNextMonth = (ImageView) findViewById(R.id.iv_next_month);
        mCalendarRecyclerView = (RecyclerView) findViewById(R.id.calendar_recycler_view);
        mIvPreMonth.setOnClickListener(this);
        mIvNextMonth.setOnClickListener(this);
        mTvCurrentDay.setText(getDateStr(currentYear, currentMonth));
        mCalendarRecyclerView.setLayoutManager(mGridLayoutManager);
        mCalendarRecyclerView.setAdapter(mCalendarAdapter);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = (ViewGroup) findViewById(mContentViewId);

    }


    private float downY;
    private float mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mContentView == null) {
            return super.onInterceptTouchEvent(ev);
        }
        int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                Log.d("onIntercept", dy + "");
                if (dy < 0) {//向上滑动
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mContentView == null) {
            return false;
        }
        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = downY = y;
                Log.d("onTouch y=", y + "");
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                Log.d("onTouch", dy + "");
                if (dy < 0) {//向上滑动
                    mContentView.onTouchEvent(event);
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d("onTouch y=", y - downY + "");
                if (y - downY < 0) {
                    mContentView.setTranslationY(y - downY );
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
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

    private void setData(int currentYear, int currentMonth, int currentMontOfDays, int firstDayWeek) {
        if (!mDataList.isEmpty()) {
            mDataList.clear();
        }
        if (firstDayWeek != 6) {
            for (int i = 0; i <= firstDayWeek; i++) {
                mDataList.add(new CalendarBean(0, false, true, ""));
            }
        }
        for (int i = 1; i <= currentMontOfDays; i++) {
            if (i == currentDay && currentMonth == month && year == currentYear) {
                mDataList.add(new CalendarBean(i, true, false, getDateStr(currentYear, currentMonth, i)));
            } else {
                mDataList.add(new CalendarBean(i, false, false, getDateStr(currentYear, currentMonth, i)));
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
