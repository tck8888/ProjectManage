package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：38
 */

public class ScheduleListBean {

    private List<VisitBean> visit;
    private List<MemoBean> memo;

    public List<VisitBean> getVisit() {
        return visit;
    }

    public void setVisit(List<VisitBean> visit) {
        this.visit = visit;
    }

    public List<MemoBean> getMemo() {
        return memo;
    }

    public void setMemo(List<MemoBean> memo) {
        this.memo = memo;
    }

    public static class VisitBean implements ItemType {
        private String project_name;
        private List<SubjectsBean> subjects;

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public List<SubjectsBean> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<SubjectsBean> subjects) {
            this.subjects = subjects;
        }

        @Override
        public boolean isVisit() {
            return true;
        }
    }


}

