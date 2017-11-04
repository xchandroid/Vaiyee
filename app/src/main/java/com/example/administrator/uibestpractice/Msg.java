package com.example.administrator.uibestpractice;

/**
 * Created by Administrator on 2017/7/26.
 */

public class Msg {
    public static final int receivde=0;
    public static final int send=1;
    String context;
    int type;
    public Msg(String context,int type){
        this.context=context;
        this.type=type;
    }
    public String getContex(){
        return context;
    }
    public int getType(){
        return type;
    }
}
