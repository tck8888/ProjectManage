package com.healthmudi.bean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class FileListBean {

    public String fileGroupName;
    public List<FileSubsBean> mFileSubsBeanList;

    public FileListBean() {
    }

    public FileListBean(String fileGroupName, List<FileSubsBean> fileSubsBeanList) {
        this.fileGroupName = fileGroupName;
        mFileSubsBeanList = fileSubsBeanList;
    }

    public static class FileSubsBean {
        public String fileChildName;

        public FileSubsBean() {
        }

        public FileSubsBean(String fileChildName) {
            this.fileChildName = fileChildName;
        }
    }
}
