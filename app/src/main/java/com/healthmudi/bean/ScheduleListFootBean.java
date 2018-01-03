package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：51
 */

public class ScheduleListFootBean implements ItemType{

    private String project_name;

    private List<MemoBean> memo;

    public ScheduleListFootBean() {
    }

    public ScheduleListFootBean(String project_name, List<MemoBean> memo) {
        this.project_name = project_name;
        this.memo = memo;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public List<MemoBean> getMemo() {
        return memo;
    }

    public void setMemo(List<MemoBean> memo) {
        this.memo = memo;
    }

    @Override
    public boolean isVisit() {
        return false;
    }
}
