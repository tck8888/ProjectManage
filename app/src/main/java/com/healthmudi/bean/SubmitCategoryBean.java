package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/26 17：35
 */

public class SubmitCategoryBean {

    private String category;
    private List<String> items;

    public SubmitCategoryBean() {
    }

    public SubmitCategoryBean(String category, List<String> items) {
        this.category = category;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
