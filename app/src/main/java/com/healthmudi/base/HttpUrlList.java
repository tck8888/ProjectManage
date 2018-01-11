package com.healthmudi.base;

/**
 * Created by tck
 * Date: 2017/12/20 10：21
 */

public interface HttpUrlList {

    String PRODUCTION_BASE_URL="http://140.207.75.158:3000/";
    String TEST_BASE_URL="http://192.168.1.110:3000/";
    String BASE_URL = "http://192.168.1.110:3000/";
    //String BASE_URL = "http://140.207.75.158:3000/";

    String PROJECT_LIST_URL = "project/list";//我的项目
    String PROJECT_SUBJECT_LIST_URL = "project/subject/list";//项目受试者
    String PROJECT_SUBJECT_FILE_LIST_URL = "project/file/list";//文件列表
    String PROJECT_CLOCK_IN_HISTORY_URL = "project/clock_in/history";//打卡历史
    String PROJECT_CLOCK_IN_NEARBY_SITE_URL = "project/clock_in/nearby_site";//打卡机构
    String PROJECT_CLOCK_IN_URL = "project/clock_in";//项目-打卡
    String PROJECT_CLOCK_ENROLL_URL = "project/subject/enroll";//受试者入组
    String PROJECT_VISIT_LIST_URL = "project/subject/visit/list";//受试者入组列表
    String PROJECT_VISIT_OUTPLAN_URL = "project/subject/visit/outplan";//受试者计划外访
    String PROJECT_VISIT_END_URL = "project/subject/visit/end";//研究结束信息
    String PROJECT_VISIT_SUBMIT_URL = "project/subject/visit/submit";//受试者访视提交
    String PROJECT_SUBJECT_DEL_URL = "project/subject/del";//项目-受试者删除
    String PROJECT_SUBJECT_SEARCH_URL = "project/subject/search";//项目-受试者搜索
    String PROJECT_SUBJECT_CODES_URL = "project/subject/codes";//项目-受试者编号列表
    String PROJECT_SUBJECT_VISIT_TIP_URL = "project/subject/visit/tip";//项目-受试者访视注意事项


    String PROJECT_JOB_LIST_URL = "project/job/list";//项目-工时列表
    String PROJECT_JOB_SITE_APPROVE_URL = "project/job/site_approve";//项目-工时-机构立项
    String PROJECT_JOB_EC_SUBMIT_URL = "project/job/ec_submit";//项目-工时-伦理递交
    String PROJECT_CONFIG_EC_DOC_URL = "project/config/ec_doc";//项目-工时-伦理递交材料
    String PROJECT_JOB_CONT_FOLLOW_URL = "project/job/cont_follow";//项目-工时-合同跟进
    String PROJECT_JOB_KICK_OFF_URL = "project/job/kick_off";//项目-工时-项目启动会
    String PROJECT_JOB_SAE_REP_URL = "project/job/sae_rep";//项目-工时-SAE上报
    String PROJECT_JOB_SAE_TIP_URL = "project/job/sae_tip";//项目-工时-SAE上报注意事项
    String PROJECT_JOB_SUBJECT_FILTER_URL = "project/job/subject_filter";//项目-工时-受试者预筛
    String PROJECT_JOB_SUBJECT_VISIT_URL = "project/job/subject_visit";//项目-工时-受试者访规
    String PROJECT_JOB_EDC_FILL_URL = "project/job/edc_fill";//项目-工时-EDC填写
    String PROJECT_JOB_SERVER_CONF_URL = "project/job/server_conf";//项目-工时-后台配置特殊工作
    String PROJECT_JOB_OTHER_WORK_URL = "project/job/other_work";//项目-工时-其它工作
    String PROJECT_JOB_ITEMS_URL = "project/job/items";//项目-工时工作项目

    String SCHEDULE_ADD_URL = "schedule/add"; //日程-添加
    String SCHEDULE_UPDATE_URL = "schedule/update"; //日程-状态更新
    String SCHEDULE_DEL_URL = "schedule/del"; //日程-删除
    String SCHEDULE_LIST_URL = "schedule/list"; //日程-列表


}
