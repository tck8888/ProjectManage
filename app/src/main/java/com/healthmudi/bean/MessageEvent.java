package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/21 17：05
 */

public class MessageEvent<T> {

    public static final String KEY_ENTRY_GROUP_BASIC_INFORMATION_SUCCESS = "EntryGroupBasicInformationActivity_SUCCESS";
    private String tag;
    private T data ;

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
}
