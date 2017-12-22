package com.healthmudi.bean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class SubjectsPersonalListBean {

    private SubjectBean subject;
    private List<VisitBean> visit;

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public List<VisitBean> getVisit() {
        return visit;
    }

    public void setVisit(List<VisitBean> visit) {
        this.visit = visit;
    }

    public static class SubjectBean {
        private String subject_filter_id;
        private String subject_code;
        private String name_py;
        private String mobile;
        private int baseline_type;
        private String baseline_date;
        private String arm_code;
        private String end_date;
        private String end_reason;
        private String end_reason_description;
        private String site_name;

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

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
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

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }
    }

    public static class VisitBean {
        private int subject_visit_id;
        private int visit_id;
        private String visit_name;
        private int target_visit_time;
        private int window_neg;
        private int window_pos;
        private int actual_visit_time;
        private int not_finish_flag;
        private int visit_type;
        private String remark;
        private String actual_visit_content;
        private List<VisitContentBean> visit_content;

        public int getSubject_visit_id() {
            return subject_visit_id;
        }

        public void setSubject_visit_id(int subject_visit_id) {
            this.subject_visit_id = subject_visit_id;
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

        public int getTarget_visit_time() {
            return target_visit_time;
        }

        public void setTarget_visit_time(int target_visit_time) {
            this.target_visit_time = target_visit_time;
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

        public int getActual_visit_time() {
            return actual_visit_time;
        }

        public void setActual_visit_time(int actual_visit_time) {
            this.actual_visit_time = actual_visit_time;
        }

        public int getNot_finish_flag() {
            return not_finish_flag;
        }

        public void setNot_finish_flag(int not_finish_flag) {
            this.not_finish_flag = not_finish_flag;
        }

        public int getVisit_type() {
            return visit_type;
        }

        public void setVisit_type(int visit_type) {
            this.visit_type = visit_type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getActual_visit_content() {
            return actual_visit_content;
        }

        public void setActual_visit_content(String actual_visit_content) {
            this.actual_visit_content = actual_visit_content;
        }

        public List<VisitContentBean> getVisit_content() {
            return visit_content;
        }

        public void setVisit_content(List<VisitContentBean> visit_content) {
            this.visit_content = visit_content;
        }

        public static class VisitContentBean {
            private String category;
            private List<ItemsBean> items;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                private String category;
                private String item_en;
                private String item_cn;

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public String getItem_en() {
                    return item_en;
                }

                public void setItem_en(String item_en) {
                    this.item_en = item_en;
                }

                public String getItem_cn() {
                    return item_cn;
                }

                public void setItem_cn(String item_cn) {
                    this.item_cn = item_cn;
                }
            }
        }
    }
}
