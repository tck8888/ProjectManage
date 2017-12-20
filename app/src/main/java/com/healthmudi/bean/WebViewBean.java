package com.healthmudi.bean;

import java.io.Serializable;

/**
 * Created by tck
 * Date: 2017/12/20 17：12
 */

public class WebViewBean implements Serializable {

    private String url;
    private String title;

    public WebViewBean() {
    }

    public WebViewBean(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
