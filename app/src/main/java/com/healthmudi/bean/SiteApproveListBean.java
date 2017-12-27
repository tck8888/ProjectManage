package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2017/12/27 09：57
 */

public class SiteApproveListBean {

    private int id;
    private String name;
    private boolean isSeleted = false;

    public SiteApproveListBean(int id,String name, boolean isSeleted) {
        this.id = id;
        this.name = name;
        this.isSeleted = isSeleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }
}
