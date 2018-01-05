package com.healthmudi.home.home_fragment.adapter;

/**
 * decription:
 * Created by tck on 2018/1/5.
 */

public class CalendarBean {
    private int day;
    private boolean isCurrentDay = false;
    private boolean isEmpty = false;

    public CalendarBean(int day, boolean isCurrentDay, boolean isEmpty) {
        this.day = day;
        this.isCurrentDay = isCurrentDay;
        this.isEmpty = isEmpty;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
