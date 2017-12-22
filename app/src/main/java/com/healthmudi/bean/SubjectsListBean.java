package com.healthmudi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/9.
 */

public class SubjectsListBean {

    private String site_name;
    private List<SubjectsBean> subjects;

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean implements Serializable{
        private int subject_id;
        private int project_id;
        private int site_id;
        private String subject_filter_id;
        private String subject_code;
        private String name_py;
        private String mobile;
        private int baseline_type;
        private String baseline_date;
        private String arm_code;
        private Object end_date;
        private Object remark;
        private Object end_reason_description;
        private String site_name;

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public int getSite_id() {
            return site_id;
        }

        public void setSite_id(int site_id) {
            this.site_id = site_id;
        }

        public String getSubject_filter_id() {
            return subject_filter_id;
        }

        public void setSubject_filter_id(String subject_filter_id) {
            this.subject_filter_id = subject_filter_id;
        }

        public String getSubject_code() {
            return subject_code;
        }

        public void setSubject_code(String subject_code) {
            this.subject_code = subject_code;
        }

        public String getName_py() {
            return name_py;
        }

        public void setName_py(String name_py) {
            this.name_py = name_py;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getBaseline_type() {
            return baseline_type;
        }

        public void setBaseline_type(int baseline_type) {
            this.baseline_type = baseline_type;
        }

        public String getBaseline_date() {
            return baseline_date;
        }

        public void setBaseline_date(String baseline_date) {
            this.baseline_date = baseline_date;
        }

        public String getArm_code() {
            return arm_code;
        }

        public void setArm_code(String arm_code) {
            this.arm_code = arm_code;
        }

        public Object getEnd_date() {
            return end_date;
        }

        public void setEnd_date(Object end_date) {
            this.end_date = end_date;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getEnd_reason_description() {
            return end_reason_description;
        }

        public void setEnd_reason_description(Object end_reason_description) {
            this.end_reason_description = end_reason_description;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }
    }
}
