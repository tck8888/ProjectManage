package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/19 15：19
 */

public class ReportFormDetailBean {


    private String index;
    private String result;

    public ReportFormDetailBean(String index, String result) {
        this.index = index;
        this.result = result;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
