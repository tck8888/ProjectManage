package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：38
 */

public class ScheduleList1Bean {


    private int timestamp;
    private List<VisitBean> visit;
    private List<MemoBean> memo;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<VisitBean> getVisit() {
        return visit;
    }

    public void setVisit(List<VisitBean> visit) {
        this.visit = visit;
    }

    public List<MemoBean> getMemo() {
        return memo;
    }

    public void setMemo(List<MemoBean> memo) {
        this.memo = memo;
    }

    public static class VisitBean {
        private String arm_code;
        private String arm_name;
        private int baseline_date;
        private int baseline_type;
        private Object end_date;
        private Object end_reason;
        private Object end_reason_description;
        private String mobile;
        private String name_py;
        private int project_id;
        private String project_name;
        private String remark;
        private int site_id;
        private String site_name;
        private String subject_code;
        private String subject_filter_id;
        private int subject_id;
        private List<VisitsBean> visits;
        private boolean isMemo = false;

        public boolean isMemo() {
            return isMemo;
        }

        public void setMemo(boolean memo) {
            isMemo = memo;
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

        public int getBaseline_date() {
            return baseline_date;
        }

        public void setBaseline_date(int baseline_date) {
            this.baseline_date = baseline_date;
        }

        public int getBaseline_type() {
            return baseline_type;
        }

        public void setBaseline_type(int baseline_type) {
            this.baseline_type = baseline_type;
        }

        public Object getEnd_date() {
            return end_date;
        }

        public void setEnd_date(Object end_date) {
            this.end_date = end_date;
        }

        public Object getEnd_reason() {
            return end_reason;
        }

        public void setEnd_reason(Object end_reason) {
            this.end_reason = end_reason;
        }

        public Object getEnd_reason_description() {
            return end_reason_description;
        }

        public void setEnd_reason_description(Object end_reason_description) {
            this.end_reason_description = end_reason_description;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName_py() {
            return name_py;
        }

        public void setName_py(String name_py) {
            this.name_py = name_py;
        }

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getSubject_code() {
            return subject_code;
        }

        public void setSubject_code(String subject_code) {
            this.subject_code = subject_code;
        }

        public String getSubject_filter_id() {
            return subject_filter_id;
        }

        public void setSubject_filter_id(String subject_filter_id) {
            this.subject_filter_id = subject_filter_id;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public List<VisitsBean> getVisits() {
            return visits;
        }

        public void setVisits(List<VisitsBean> visits) {
            this.visits = visits;
        }
    }

}

