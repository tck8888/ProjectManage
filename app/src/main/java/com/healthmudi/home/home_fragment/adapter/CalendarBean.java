package com.healthmudi.home.home_fragment.adapter;

/**
 * decription:
 * Created by tck on 2018/1/5.
 */

public class CalendarBean {
    private int day;
    private boolean isCurrentDay = false;
    private boolean isEmpty = false;
    private String dateStr;
    private boolean isChecked= false;

    public CalendarBean(int day, boolean isCurrentDay, boolean isEmpty,String dateStr) {
        this.day = day;
        this.isCurrentDay = isCurrentDay;
        this.isEmpty = isEmpty;
        this.dateStr = dateStr;
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

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
