package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/21 17：05
 */

public class MessageEvent<T> {


    private String tag;
    private T data;

    public MessageEvent() {
    }

    public MessageEvent(String tag) {
        this.tag = tag;
    }

    public MessageEvent(String tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static final String KEY_ENTRY_GROUP_BASIC_INFORMATION_ADD_SUCCESS = "EntryGroupBasicInformationActivity_add_SUCCESS";
    public static final String KEY_ENTRY_GROUP_BASIC_INFORMATION_UPDATE_SUCCESS = "EntryGroupBasicInformationActivity_update_SUCCESS";
    public static final String KEY_PLANNED_INTERVIEW_SUCCESS = "PlannedInterviewActivity_SUCCESS";
    public static final String KEY_RESEARCH_END_VISIT_SUCCESS = "ResearchEndVisitActivity_SUCCESS";
    public static final String KEY_REGULAR_VISITS_SUCCESS = "RegularVisitsActivity_SUCCESS";

    public static final String KEY_INSTITUTION_ESTABLISHMENT_SUCCESS = "InstitutionEstablishmentActivity_SUCCESS";
    public static final String KEY_ETHICAL_SUBMISSION_SUCCESS = "EthicalSubmissionActivity_SUCCESS";
    public static final String KEY_CONTRACT_FOLLOW_UP_SUCCESS = "ContractFollowUpActivity_SUCCESS";
    public static final String KEY_PROJECT_START_MEETING_SUCCESS = "ProjectStartMeetingActivity_SUCCESS";
    public static final String KEY_SAE_REPORT_SUCCESS = "SaeReportActivity_SUCCESS";
    public static final String KEY_PRESIFTING_SUCCESS = "PresiftingActivity_SUCCESS";
    public static final String KEY_VISITORS_VISIT_TO_THE_RULES_SUCCESS = "VisitorsVisitToTheRulesActivity_SUCCESS";
    public static final String KEY_EDC_FILL_IN_SUCCESS = "EDCFillInActivity_SUCCESS";
    public static final String KEY_OTHER_WORK_SUCCESS = "OtherWorkActivity_SUCCESS";
    public static final String KEY_SERVER_CONF_SUCCESS = "ServerConfActivity_SUCCESS";

    public static final String KEY_MEMORANDUM_ADD_SUCCESS = "MemorandumAddActivity_SUCCESS";//备忘录添加
    public static final String KEY_MEMORANDUM_SEE_SUCCESS = "MemorandumSeeActivity_SUCCESS";//备忘录查看

    public static final String KEY_SELECT_DOC_SUCCESS = "select_doc_SUCCESS";
    public static final String KEY_SELECT_SUBJECT_SUCCESS = "select_subject_SUCCESS";

    public static final String KEY_LOCATION_SETTINGS = "location_settings";
    public static final String KEY_LOCATION_PERMISSIONS = "location_permissions";

    public static final String KEY_PUNCH_CLOCK_SELECT_LOCATION_SUCCESS = "PunchClockSelectLocationActivity_SUCCESS";
}
