package com.healthmudi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class SubjectsPersonalListBean implements Serializable {


    private int subject_visit_id;
    private int visit_id;
    private String visit_name;
    private long target_visit_time;
    private int window_neg;
    private int window_pos;
    private long actual_visit_time;
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

    public long getTarget_visit_time() {
        return target_visit_time;
    }

    public void setTarget_visit_time(long target_visit_time) {
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


}
