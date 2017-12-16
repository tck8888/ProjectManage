package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/16 10：23
 */

public class SignHistoryListBean {

    private String hospitalName;
    private String hospitalTime;

    public SignHistoryListBean(String hospitalName, String hospitalTime) {
        this.hospitalName = hospitalName;
        this.hospitalTime = hospitalTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalTime() {
        return hospitalTime;
    }

    public void setHospitalTime(String hospitalTime) {
        this.hospitalTime = hospitalTime;
    }
}
