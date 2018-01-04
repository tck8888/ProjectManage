package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：51
 */

public class ScheduleListVisitBean {


    private String project_name;
    private List<ScheduleSubject> scheduleSubject;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public List<ScheduleSubject> getScheduleSubject() {
        return scheduleSubject;
    }

    public void setScheduleSubject(List<ScheduleSubject> scheduleSubject) {
        this.scheduleSubject = scheduleSubject;
    }

    public static class ScheduleSubject {
        private String arm_code;
        private int baseline_date;
        private int baseline_type;
        private long end_date;
        private String end_reason;
        private String end_reason_description;
        private String mobile;
        private String name_py;
        private int project_id;
        private String remark;
        private int site_id;
        private String site_name;
        private String subject_code;
        private String subject_filter_id;
        private int subject_id;

        private long actual_visit_time;
        private int not_finish_flag;
        private String visits_remark;
        private int subject_visit_id;
        private int target_visit_time;
        private int visit_id;
        private String visit_name;
        private int visit_type;
        private int window_neg;
        private int window_pos;
        private List<VisitContentBean> visit_content;

        public String getArm_code() {
            return arm_code;
        }

        public void setArm_code(String arm_code) {
            this.arm_code = arm_code;
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

        public long getEnd_date() {
            return end_date;
        }

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }

        public String getEnd_reason() {
            return end_reason;
        }

        public void setEnd_reason(String end_reason) {
            this.end_reason = end_reason;
        }

        public String getEnd_reason_description() {
            return end_reason_description;
        }

        public void setEnd_reason_description(String end_reason_description) {
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

        public long getActual_visit_time() {
            return actual_visit_time;
        }

        public void setActual_visit_time(long actual_visit_time) {
            this.actual_visit_time = actual_visit_time;
        }

        public int getNot_finish_flag() {
            return not_finish_flag;
        }

        public void setNot_finish_flag(int not_finish_flag) {
            this.not_finish_flag = not_finish_flag;
        }

        public String getVisits_remark() {
            return visits_remark;
        }

        public void setVisits_remark(String visits_remark) {
            this.visits_remark = visits_remark;
        }

        public int getSubject_visit_id() {
            return subject_visit_id;
        }

        public void setSubject_visit_id(int subject_visit_id) {
            this.subject_visit_id = subject_visit_id;
        }

        public int getTarget_visit_time() {
            return target_visit_time;
        }

        public void setTarget_visit_time(int target_visit_time) {
            this.target_visit_time = target_visit_time;
        }

        public int getVisit_id() {
            return visit_id;
        }

        public void setVisit_id(int visit_id) {
            this.visit_id = visit_id;
        }

        public String getVisit_name() {
            return visit_name;
        }

        public void setVisit_name(String visit_name) {
            this.visit_name = visit_name;
        }

        public int getVisit_type() {
            return visit_type;
        }

        public void setVisit_type(int visit_type) {
            this.visit_type = visit_type;
        }

        public int getWindow_neg() {
            return window_neg;
        }

        public void setWindow_neg(int window_neg) {
            this.window_neg = window_neg;
        }

        public int getWindow_pos() {
            return window_pos;
        }

        public void setWindow_pos(int window_pos) {
            this.window_pos = window_pos;
        }

        public List<VisitContentBean> getVisit_content() {
            return visit_content;
        }

        public void setVisit_content(List<VisitContentBean> visit_content) {
            this.visit_content = visit_content;
        }
    }


}
