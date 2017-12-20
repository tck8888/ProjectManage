package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/20 10：37
 */

public class ProjectListBean {

    private int project_id;
    private String project_name;
    private int arrive_time;
    private String arrive_site_name;
    private int leave_time;
    private String leave_site_name;
    private String arm_code;
    private String arm_name;

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(int arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getArrive_site_name() {
        return arrive_site_name;
    }

    public void setArrive_site_name(String arrive_site_name) {
        this.arrive_site_name = arrive_site_name;
    }

    public int getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(int leave_time) {
        this.leave_time = leave_time;
    }

    public String getLeave_site_name() {
        return leave_site_name;
    }

    public void setLeave_site_name(String leave_site_name) {
        this.leave_site_name = leave_site_name;
    }

    public String getArm_code() {
        return arm_code;
    }

    public void setArm_code(String arm_code) {
        this.arm_code = arm_code;
    }

    public String getArm_name() {
        return arm_name;
    }

    public void setArm_name(String arm_name) {
        this.arm_name = arm_name;
    }
}
