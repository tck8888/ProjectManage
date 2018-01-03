package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：48
 */

public class VisitContentBean {
    private String category;
    private List<ItemsBean> items;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }
}
