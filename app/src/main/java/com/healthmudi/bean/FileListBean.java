package com.healthmudi.bean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class FileListBean {

    private String folder;
    private List<FilesBean> files;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        private int project_id;
        private String folder_name;
        private String file_name;
        private String file_path;

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getFolder_name() {
            return folder_name;
        }

        public void setFolder_name(String folder_name) {
            this.folder_name = folder_name;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }
}
