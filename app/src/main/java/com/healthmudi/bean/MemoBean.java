package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2018/01/03 13：31
 */

public class MemoBean {

    private int memo_id;
    private String memo_content;
    private int status;
    private long memo_time;

    public int getMemo_id() {
        return memo_id;
    }

    public void setMemo_id(int memo_id) {
        this.memo_id = memo_id;
    }

    public String getMemo_content() {
        return memo_content;
    }

    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getMemo_time() {
        return memo_time;
    }

    public void setMemo_time(long memo_time) {
        this.memo_time = memo_time;
    }


}
