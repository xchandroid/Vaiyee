package com.example.administrator.uibestpractice;

/**
 * Created by Administrator on 2017/9/14.
 */

public class Lianxirenshuju {
    private String haoma;
    private  String xingming;

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getHaoma() {

        return haoma;
    }

    public void setHaoma(String haoma) {
        this.haoma = haoma;
    }

    public  Lianxirenshuju(String xingming, String haoma){
          this.haoma=haoma;
        this.xingming=xingming;
    }

}
