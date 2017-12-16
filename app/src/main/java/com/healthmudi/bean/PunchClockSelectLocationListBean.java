package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/16 13：15
 */

public class PunchClockSelectLocationListBean {

    private String hospitalName;
    private String distance;
    private String location;

    private boolean isSelected = false;

    public PunchClockSelectLocationListBean(String hospitalName, String distance, String location) {
        this.hospitalName = hospitalName;
        this.distance = distance;
        this.location = location;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
