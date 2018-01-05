package com.healthmudi.utils;

import android.support.annotation.Nullable;

/**
 * Created by tck
 * Date: 2017/12/26 14：36
 */

public class StringConvertCodeEachUtils {

    public static String getString(@Nullable int code) {
        String str = "";
        switch (code) {
            case 1:
                str = "上次实际访视日期";
                break;
            case 2:
                str = "知情同意书签署日期";
                break;
            case 3:
                str = "随机分组日期";
                break;
            case 4:
                str = "首次服药日期";
                break;
        }
        return str;
    }


    public static String getString(@Nullable String type) {
        String str = "";
        switch (type) {
            case "上次实际访视日期":
                str = "1";
                break;
            case "知情同意书签署日期":
                str = "2";
                break;
            case "随机分组日期":
                str = "3";
                break;
            case "首次服药日期":
                str = "4";
                break;
        }
        return str;
    }

    public static String getWorkContent(@Nullable int type) {
        String str = "";
        switch (type) {
            case 1:
                str = "机构立项";
                break;
            case 2:
                str = "伦理递交";
                break;
            case 3:
                str = "合同跟进";
                break;
            case 4:
                str = "项目启动会";
                break;
            case 5:
                str = "SAE上报";
                break;
            case 6:
                str = "受试者预筛";
                break;
            case 7:
                str = "受试者访视";
                break;
            case 8:
                str = "EDC填写";
                break;
            case 9:
                str = "后台配置特殊工作1";
                break;
            case 10:
                str = "其它工作";
                break;
        }
        return str;
    }


    public static String getWorkConetntStatus(@Nullable String workConetnt) {

        if (workConetnt.contains("材料已递交，待审批")
                || workConetnt.contains("已递交至PI,等待PI签字中")
                || workConetnt.contains("PI已完成签字，待递交至EC")
                || workConetnt.contains("已递交至EC，待审批")
                || workConetnt.contains("材料已递交，待审批")) {
            return "未完成";
        } else {
            return "已完成";
        }
    }

    public static String getInstitutionEstablishmentStr(@Nullable int code) {
        switch (code) {
            case 1:
                return "材料已递交，待审批";
            case 2:
                return "机构立项审批已完成";
            default:
                return "";
        }
    }

    public static String getEthicalSubmission(@Nullable int code) {
        switch (code) {
            case 1:
                return "已递交至PI,等待PI签字中";
            case 2:
                return "PI已完成签字，待递交至EC";
            case 3:
                return "已递交至EC，待审批";
            case 4:
                return "EC已审批";
            default:
                return "";
        }
    }


    public static String getContractFollowUp(@Nullable int code) {
        switch (code) {
            case 1:
                return "合同已递交机构，待审核";
            case 2:
                return "合同已审核通过，待双方签署";
            case 3:
                return "申办方已签署，待机构签署";
            case 4:
                return "机构已签署，待申办方签署";
            case 5:
                return "双方已签署，合同已完成";
            default:
                return "";
        }
    }

}
