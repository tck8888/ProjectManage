
package com.healthmudi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.healthmudi.R;
import com.healthmudi.home.home_fragment.adapter.CalendarAdapter;
import com.healthmudi.home.home_fragment.adapter.CalendarBean;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/16 17：14
 */

public class ScheduleCalendarView1 extends LinearLayout {


    private RecyclerView mCalendarRecyclerView;

    private int currentDay;
    private int currentYear;
    private int currentMonth;
    private int currentMontOfDays;
    private int firstDayWeek;
    private int month;
    private int year;

    private List<CalendarBean> mDataList = new ArrayList<>();
    private CalendarAdapter mCalendarAdapter;
    private GridLayoutManager mGridLayoutManager;

    private int mContentViewId;
    private int mContentViewTranslateY; //ContentView  可滑动的最大距离距离 , 固定
    /**
     * ContentView
     */
    ViewGroup mContentView;

    /**
     * 手速判断
     */
    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;


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

        setOrientation(LinearLayout.VERTICAL);

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
        mCalendarRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_recycler_view);

        mCalendarRecyclerView.setLayoutManager(mGridLayoutManager);
        mCalendarRecyclerView.setAdapter(mCalendarAdapter);

        mCalendarAdapter.setOnItemClickListener(new CalendarAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mOnDateClickListener != null) {
                    mOnDateClickListener.onClick(mDataList.get(position));
                }
                setSelectPosition(position);
            }
        });

        mVelocityTracker = VelocityTracker.obtain();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public interface OnDateClickListener {
        void onClick(CalendarBean position);
    }

    private OnDateClickListener mOnDateClickListener;

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        mOnDateClickListener = onDateClickListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = (ViewGroup) findViewById(mContentViewId);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mContentView != null) {
            int h = getHeight() - 2 * CommonUtils.dipToPx(getContext(), 41);
            int heightSpec = MeasureSpec.makeMeasureSpec(h,
                    MeasureSpec.EXACTLY);
            mContentView.measure(widthMeasureSpec, heightSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentViewTranslateY = mCalendarRecyclerView.getMeasuredHeight() - CommonUtils.dipToPx(getContext(), 40);
    }

    private float downY;
    private float mLastY;
    private int mTouchSlop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isAnimating) {
            return true;
        }

        if (mContentView == null || mContentView.getVisibility() != VISIBLE) {
            return super.onInterceptTouchEvent(ev);
        }
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                 /*
                   如果向上滚动，且ViewPager已经收缩，不拦截事件
                 */
                if (dy < 0 && mContentView.getTranslationY() == -mContentViewTranslateY) {
                    return false;
                }
                /*
                  * 如果向下滚动，有 2 种情况处理 且y在ViewPager下方
                  * 1、RecyclerView 或者其它滚动的View，当mContentView滚动到顶部时，拦截事件
                  * 2、非滚动控件，直接拦截事件
                */
                if (dy > 0 && mContentView.getTranslationY() == -mContentViewTranslateY
                        && y >= CommonUtils.dipToPx(getContext(), 98)) {
                    if (!isScrollTop())
                        return false;
                }

                if (dy > 0 && mContentView.getTranslationY() == 0 && y >= CommonUtils.dipToPx(getContext(), 98)) {
                    return false;
                }

                if (Math.abs(dy) > mTouchSlop) {//大于mTouchSlop开始拦截事件，ContentView和ViewPager得到CANCEL事件
                    if ((dy > 0 && mContentView.getTranslationY() <= 0)
                            || (dy < 0 && mContentView.getTranslationY() >= -mContentViewTranslateY)) {
                        mLastY = y;
                        return true;
                    }
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
        if (mContentView == null)
            return false;
        int action = event.getAction();
        float y = event.getY();
        mVelocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = downY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                if (dy < 0 && mContentView.getTranslationY() == -mContentViewTranslateY) {
                    mContentView.onTouchEvent(event);
                    //showWeek();
                    return false;
                }
                //hideWeek();

                if (dy > 0 && mContentView.getTranslationY() + dy >= 0) {
                    mContentView.setTranslationY(0);
                    translationViewPager();
                    return super.onTouchEvent(event);
                }
                if (dy < 0 && mContentView.getTranslationY() + dy <= -mContentViewTranslateY) {
                    mContentView.setTranslationY(-mContentViewTranslateY);
                    translationViewPager();
                    return super.onTouchEvent(event);
                }
                mContentView.setTranslationY(mContentView.getTranslationY() + dy);
                translationViewPager();
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                float mYVelocity = velocityTracker.getYVelocity();
                if (mContentView.getTranslationY() == 0
                        || mContentView.getTranslationY() == mContentViewTranslateY) {
                    break;
                }
                if (Math.abs(mYVelocity) >= 800) {
                    if (mYVelocity < 0) {
                        shrink();
                    } else {
                        expand();
                    }
                    return super.onTouchEvent(event);
                }
                if (event.getY() - downY > 0) {
                    expand();
                } else {
                    shrink();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private int mItemHeight = CommonUtils.dipToPx(getContext(), 40);
    private int mViewPagerTranslateY = mItemHeight;


    public void setSelectPosition(int selectPosition) {
        int line = selectPosition / 7;
        mViewPagerTranslateY = line * mItemHeight;
    }

    private void translationViewPager() {
        float percent = mContentView.getTranslationY() * 1.0f / mContentViewTranslateY;
        mCalendarRecyclerView.setTranslationY(mViewPagerTranslateY * percent);
    }

    private boolean isAnimating = false;

    /**
     * 展开
     */
    public void expand() {

        if (isAnimating)
            return;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mContentView,
                "translationY", mContentView.getTranslationY(), 0f);
        objectAnimator.setDuration(240);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (Float) animation.getAnimatedValue();
                float percent = currentValue * 1.0f / mContentViewTranslateY;
                mCalendarRecyclerView.setTranslationY(mViewPagerTranslateY * percent);
                isAnimating = true;
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;

            }
        });
        objectAnimator.start();
    }


    /**
     * 收缩
     */
    public void shrink() {

        if (isAnimating)
            return;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mContentView,
                "translationY", mContentView.getTranslationY(), -mContentViewTranslateY);
        objectAnimator.setDuration(240);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (Float) animation.getAnimatedValue();
                float percent = currentValue * 1.0f / mContentViewTranslateY;
                mCalendarRecyclerView.setTranslationY(mViewPagerTranslateY * percent);
                isAnimating = true;
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;

            }
        });
        objectAnimator.start();
    }


    public void changePreMonth() {
        if (currentMonth - 1 == 0) {
            currentYear = currentYear - 1;
            currentMonth = 12;
        } else {
            currentMonth = currentMonth - 1;
        }
        firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);
        currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
        setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);
        mCalendarAdapter.notifyDataSetChanged();

    }

    public void changeNextMonth() {
        if (currentMonth + 1 > 12) {
            currentYear = currentYear + 1;
            currentMonth = 1;
        } else {
            currentMonth = currentMonth + 1;
        }
        firstDayWeek = DateUtils.getFirstDayWeekPosition(currentYear, currentMonth, 2);
        currentMontOfDays = DateUtils.getMonthDays(currentYear, currentMonth);
        setData(currentYear, currentMonth, currentMontOfDays, firstDayWeek);
        mCalendarAdapter.notifyDataSetChanged();
    }

    private void setData(int currentYear, int currentMonth, int currentMontOfDays, int firstDayWeek) {
        if (!mDataList.isEmpty()) {
            mDataList.clear();
        }
        if (firstDayWeek != 6) {
            for (int i = 0; i <= firstDayWeek; i++) {
                mDataList.add(new CalendarBean(0, false, true, DateUtils.getDateStr(currentYear, currentMonth, 0)));
            }
        }
        for (int i = 1; i <= currentMontOfDays; i++) {
            if (i == currentDay && currentMonth == month && year == currentYear) {
                mDataList.add(new CalendarBean(i, true, false, DateUtils.getDateStr(currentYear, currentMonth, i)));
                //当前今天的选中位置
                setSelectPosition(mDataList.size() - 1);
            } else {
                mDataList.add(new CalendarBean(i, false, false, DateUtils.getDateStr(currentYear, currentMonth, i)));
            }
        }
    }



    /**
     * ContentView是否滚动到顶部
     */
    private boolean isScrollTop() {
        if (mContentView instanceof RecyclerView)
            return ((RecyclerView) mContentView).computeVerticalScrollOffset() == 0;
        if (mContentView instanceof AbsListView) {
            boolean result = false;
            AbsListView listView = (AbsListView) mContentView;
            if (listView.getFirstVisiblePosition() == 0) {
                final View topChildView = listView.getChildAt(0);
                result = topChildView.getTop() == 0;
            }
            return result;
        }
        return mContentView.getScrollY() == 0;
    }



}
