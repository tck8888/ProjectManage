package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/18 16：11
 */

public class CenterListBean {

    private String centerName;
    private boolean isChecked = false;

    public CenterListBean(String centerName, boolean isChecked) {
        this.centerName = centerName;
        this.isChecked = isChecked;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
