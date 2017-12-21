package com.healthmudi.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/20 10：37
 */

public class ProjectListBean implements Serializable {


    private int project_id;
    private String project_name;
    private String arrive_time;
    private String arrive_site_name;
    private String leave_time;
    private String leave_site_name;
    private List<SiteBean> site;
    private List<ArmBean> arm;

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

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getArrive_site_name() {
        return arrive_site_name;
    }

    public void setArrive_site_name(String arrive_site_name) {
        this.arrive_site_name = arrive_site_name;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getLeave_site_name() {
        return leave_site_name;
    }

    public void setLeave_site_name(String leave_site_name) {
        this.leave_site_name = leave_site_name;
    }

    public List<SiteBean> getSite() {
        return site;
    }

    public void setSite(List<SiteBean> site) {
        this.site = site;
    }

    public List<ArmBean> getArm() {
        return arm;
    }

    public void setArm(List<ArmBean> arm) {
        this.arm = arm;
    }


    public static class ArmBean implements Serializable, IPickerViewData {
        private String arm_code;
        private String arm_name;

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

        @Override
        public String getPickerViewText() {
            return arm_name;
        }
    }

    public static class SiteBean implements Serializable , IPickerViewData{
        private int site_id;
        private String site_name;

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

        @Override
        public String getPickerViewText() {
            return site_name;
        }
    }
}
