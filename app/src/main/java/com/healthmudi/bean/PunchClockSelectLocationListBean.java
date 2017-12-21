package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/16 13：15
 */

public class PunchClockSelectLocationListBean {

    private int site_id;
    private String site_name;
    private String site_address;
    private double site_distance;
    private int site_affectiveDistance;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_address() {
        return site_address;
    }

    public void setSite_address(String site_address) {
        this.site_address = site_address;
    }

    public double getSite_distance() {
        return site_distance;
    }

    public void setSite_distance(double site_distance) {
        this.site_distance = site_distance;
    }

    public int getSite_affectiveDistance() {
        return site_affectiveDistance;
    }

    public void setSite_affectiveDistance(int site_affectiveDistance) {
        this.site_affectiveDistance = site_affectiveDistance;
    }
}
