package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2018/01/03 09：51
 */

public class ScheduleListHeadBean {

    private String date;
    private boolean isSee = false;

    public ScheduleListHeadBean() {
    }

    public ScheduleListHeadBean(String date, boolean isSee) {
        this.date = date;
        this.isSee = isSee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSee() {
        return isSee;
    }

    public void setSee(boolean see) {
        isSee = see;
    }

}
