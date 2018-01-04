package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：46
 */

public class VisitsBean {
    private long actual_visit_time;
    private int not_finish_flag;
    private String remark;
    private int subject_visit_id;
    private long target_visit_time;
    private int visit_id;
    private String visit_name;
    private int visit_type;
    private int window_neg;
    private int window_pos;
    private List<VisitContentBean> visit_content;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSubject_visit_id() {
        return subject_visit_id;
    }

    public void setSubject_visit_id(int subject_visit_id) {
        this.subject_visit_id = subject_visit_id;
    }

    public long getTarget_visit_time() {
        return target_visit_time;
    }

    public void setTarget_visit_time(long target_visit_time) {
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
