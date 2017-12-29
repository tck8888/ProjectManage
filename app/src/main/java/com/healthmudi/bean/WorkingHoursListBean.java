package com.healthmudi.bean;

import java.io.Serializable;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class WorkingHoursListBean implements Serializable{

    private String project_name;
    private String site_name;
    private int job_id;
    private String subjects_id;
    private int job_type_id;
    private String job_type_name;
    private long operation_date;
    private long doc_receive_date;
    private long pi_submit_date;
    private long pi_sign_date;
    private long site_submit_date;
    private long site_approve_date;
    private long ec_submit_date;
    private long ec_approve_date;
    private long sponsor_sign_date;
    private long site_sign_date;
    private long kick_off_date;
    private String status="";
    private int prescreen_count;
    private int meet_count;
    private int crf_pages;
    private int job_count;
    private double job_time;
    private double job_time2;
    private String remark;
    private int isdeleted;
    private long create_time;
    private String documents_name;
    private int is_finish;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getSubjects_id() {
        return subjects_id;
    }

    public void setSubjects_id(String subjects_id) {
        this.subjects_id = subjects_id;
    }

    public int getJob_type_id() {
        return job_type_id;
    }

    public void setJob_type_id(int job_type_id) {
        this.job_type_id = job_type_id;
    }

    public String getJob_type_name() {
        return job_type_name;
    }

    public void setJob_type_name(String job_type_name) {
        this.job_type_name = job_type_name;
    }

    public long getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(long operation_date) {
        this.operation_date = operation_date;
    }

    public long getDoc_receive_date() {
        return doc_receive_date;
    }

    public void setDoc_receive_date(long doc_receive_date) {
        this.doc_receive_date = doc_receive_date;
    }

    public long getPi_submit_date() {
        return pi_submit_date;
    }

    public void setPi_submit_date(long pi_submit_date) {
        this.pi_submit_date = pi_submit_date;
    }

    public long getPi_sign_date() {
        return pi_sign_date;
    }

    public void setPi_sign_date(long pi_sign_date) {
        this.pi_sign_date = pi_sign_date;
    }

    public long getSite_submit_date() {
        return site_submit_date;
    }

    public void setSite_submit_date(long site_submit_date) {
        this.site_submit_date = site_submit_date;
    }

    public long getSite_approve_date() {
        return site_approve_date;
    }

    public void setSite_approve_date(long site_approve_date) {
        this.site_approve_date = site_approve_date;
    }

    public long getEc_submit_date() {
        return ec_submit_date;
    }

    public void setEc_submit_date(long ec_submit_date) {
        this.ec_submit_date = ec_submit_date;
    }

    public long getEc_approve_date() {
        return ec_approve_date;
    }

    public void setEc_approve_date(long ec_approve_date) {
        this.ec_approve_date = ec_approve_date;
    }

    public long getSponsor_sign_date() {
        return sponsor_sign_date;
    }

    public void setSponsor_sign_date(long sponsor_sign_date) {
        this.sponsor_sign_date = sponsor_sign_date;
    }

    public long getSite_sign_date() {
        return site_sign_date;
    }

    public void setSite_sign_date(long site_sign_date) {
        this.site_sign_date = site_sign_date;
    }

    public long getKick_off_date() {
        return kick_off_date;
    }

    public void setKick_off_date(long kick_off_date) {
        this.kick_off_date = kick_off_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrescreen_count() {
        return prescreen_count;
    }

    public void setPrescreen_count(int prescreen_count) {
        this.prescreen_count = prescreen_count;
    }

    public int getMeet_count() {
        return meet_count;
    }

    public void setMeet_count(int meet_count) {
        this.meet_count = meet_count;
    }

    public int getCrf_pages() {
        return crf_pages;
    }

    public void setCrf_pages(int crf_pages) {
        this.crf_pages = crf_pages;
    }

    public int getJob_count() {
        return job_count;
    }

    public void setJob_count(int job_count) {
        this.job_count = job_count;
    }

    public double getJob_time() {
        return job_time;
    }

    public void setJob_time(double job_time) {
        this.job_time = job_time;
    }

    public double getJob_time2() {
        return job_time2;
    }

    public void setJob_time2(double job_time2) {
        this.job_time2 = job_time2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getDocuments_name() {
        return documents_name;
    }

    public void setDocuments_name(String documents_name) {
        this.documents_name = documents_name;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }
}
