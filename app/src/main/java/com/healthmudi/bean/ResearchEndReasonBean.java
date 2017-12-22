package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/22 15：45
 */

public class ResearchEndReasonBean {

    private int id;
    private String reason;
    private boolean isCheck = false;

    public ResearchEndReasonBean() {
    }

    public ResearchEndReasonBean(int id, String reason, boolean isCheck) {
        this.id = id;
        this.reason = reason;
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
