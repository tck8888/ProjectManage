package com.healthmudi.bean;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class WorkingHoursListBean {
    public String name;
    public String date;
    public String usedTime;
    public int status;

    public WorkingHoursListBean(String name, String date, String usedTime, int status) {
        this.name = name;
        this.date = date;
        this.usedTime = usedTime;
        this.status = status;
    }
}
