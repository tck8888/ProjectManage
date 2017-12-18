package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/18 09：54
 */

public class ReportFormListBean {


    private String reportFormName;
    private String projectName;
    private String date;
    private String type;

    public ReportFormListBean(String reportFormName, String projectName, String date, String type) {
        this.reportFormName = reportFormName;
        this.projectName = projectName;
        this.date = date;
        this.type = type;
    }

    public String getReportFormName() {
        return reportFormName;
    }

    public void setReportFormName(String reportFormName) {
        this.reportFormName = reportFormName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
