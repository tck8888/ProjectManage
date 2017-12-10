package com.healthmudi.bean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/9.
 */

public class SubjectsListBean {
    public String name;
    public List<SubjectsListSubBean> mSubjectsListSubBeen;

    public SubjectsListBean() {
    }

    public SubjectsListBean(String name, List<SubjectsListSubBean> subjectsListSubBeen) {
        this.name = name;
        mSubjectsListSubBeen = subjectsListSubBeen;
    }

    public static class SubjectsListSubBean {
        public SubjectsListSubBean() {
        }

        public String subName;

        public SubjectsListSubBean(String subName) {
            this.subName = subName;
        }
    }
}
