package com.healthmudi.bean;

/**
 * decription:
 * Created by tck on 2017/12/12.
 */

public class InformationListBean {

    public String name;
    public String content;

    public String date;

    public String type;

    public InformationListBean(String name, String content, String date, String type) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.type = type;
    }
}
