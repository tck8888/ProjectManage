package com.healthmudi.base;

/**
 * Created by tck
 * Date: 2017/12/20 10：21
 */

public interface HttpUrlList {

    String BASE_URL = "http://192.168.1.113:3000/";

    String PROJECT_LIST_URL = "project/list";//我的项目
    String PROJECT_SUBJECT_LIST_URL = "project/subject/list";//项目受试者
    String PROJECT_SUBJECT_FILE_LIST_URL = "project/file/list";//文件列表
    String PROJECT_CLOCK_IN_HISTORY_URL = "project/clock_in/history";//打卡历史
    String PROJECT_CLOCK_IN_NEARBY_SITE_URL = "project/clock_in/nearby_site";//打卡机构
    String PROJECT_CLOCK_ENROLL_URL = "project/subject/enroll";//受试者入组
    String PROJECT_VISIT_LIST_URL = "project/subject/visit/list";//受试者入组列表
    String PROJECT_VISIT_OUTPLAN_URL = "project/subject/visit/outplan";//受试者计划外访
    String PROJECT_VISIT_END_URL = "project/subject/visit/end";//研究结束信息

}
