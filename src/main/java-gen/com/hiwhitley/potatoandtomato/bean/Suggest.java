package com.hiwhitley.potatoandtomato.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import cn.bmob.v3.BmobObject;

/**
 * Entity mapped to table "SUGGEST".
 */
public class Suggest extends BmobObject{

    private String msg;
    private String mailOrQQ;

    public Suggest() {
    }

    public Suggest(String msg, String mailOrQQ) {
        this.msg = msg;
        this.mailOrQQ = mailOrQQ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMailOrQQ() {
        return mailOrQQ;
    }

    public void setMailOrQQ(String mailOrQQ) {
        this.mailOrQQ = mailOrQQ;
    }

}
