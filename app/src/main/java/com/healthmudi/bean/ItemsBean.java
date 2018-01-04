package com.healthmudi.bean;

import java.io.Serializable;

/**
 * Created by tck
 * Date: 2018/01/03 09：47
 */

public class ItemsBean implements Serializable {

    private String category;
    private String item_en;
    private String item_cn;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem_en() {
        return item_en;
    }

    public void setItem_en(String item_en) {
        this.item_en = item_en;
    }

    public String getItem_cn() {
        return item_cn;
    }

    public void setItem_cn(String item_cn) {
        this.item_cn = item_cn;
    }
}
