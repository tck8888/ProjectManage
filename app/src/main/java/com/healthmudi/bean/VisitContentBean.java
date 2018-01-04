package com.healthmudi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 09：48
 */

public class VisitContentBean implements Serializable {
    private boolean isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
