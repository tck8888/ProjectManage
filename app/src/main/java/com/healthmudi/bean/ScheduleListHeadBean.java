package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：51
 */

public class ScheduleListHeadBean {

    private String project_name;
    private List<VisitsBean> visits;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public List<VisitsBean> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitsBean> visits) {
        this.visits = visits;
    }
}
