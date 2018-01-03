package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：51
 */

public class ScheduleListVisitBean implements ItemType{

    private long target_visit_time;
    private List<ScheduleBean> scheduleBeen;


    public static class ScheduleBean{
        private String project_name;
        //private ScheduleSubject
    }

    @Override
    public boolean isVisit() {
        return true;
    }


}
