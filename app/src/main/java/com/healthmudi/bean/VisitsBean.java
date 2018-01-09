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
    private String name_py;
    private String subject_code;
    private List<VisitContentBean> visit_content;

    private int memo_id;
    private String memo_content;
    private int memoStatus;
    private boolean isMemo = false;

    public boolean isMemo() {
        return isMemo;
    }

    public void setMemo(boolean memo) {
        isMemo = memo;
    }
    public int getMemo_id() {
        return memo_id;
    }

    public void setMemo_id(int memo_id) {
        this.memo_id = memo_id;
    }

    public String getMemo_content() {
        return memo_content;
    }

    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }


    public int getMemoStatus() {
        return memoStatus;
    }

    public void setMemoStatus(int memoStatus) {
        this.memoStatus = memoStatus;
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

    public String getName_py() {
        return name_py;
    }

    public void setName_py(String name_py) {
        this.name_py = name_py;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }
}
