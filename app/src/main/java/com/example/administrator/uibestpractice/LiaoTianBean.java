package com.example.administrator.uibestpractice;

/**
 * Created by Administrator on 2017/8/19.
 */

public class LiaoTianBean {
    public static final int SEND=1;
    public static final int JIESHOU=2;
    private int state;
    private String message;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
