package com.healthmudi.bean;

/**
 * decription:
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionItemListBean {

    public boolean isChecked = false;
    public String name;
    public String type;

    public WorkTimeSubmissionItemListBean(boolean isChecked, String name, String type) {
        this.isChecked = isChecked;
        this.name = name;
        this.type = type;
    }
}
