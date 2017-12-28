package com.healthmudi.bean;

/**
 * decription:
 * Created by tck on 2017/12/11.
 */

public class WorkTimeSubmissionItemListBean {

    private int id;
    private String name;
    private String type;
    private boolean isCheck = false;

    public WorkTimeSubmissionItemListBean() {
    }

    public WorkTimeSubmissionItemListBean(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
