package com.healthmudi.bean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/04 16：24
 */

public class Items2Bean {

    private String category;
    private List<String> items;

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
