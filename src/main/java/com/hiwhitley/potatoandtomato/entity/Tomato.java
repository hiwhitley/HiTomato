package com.hiwhitley.potatoandtomato.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by hiwhitley on 2016/4/4.
 */
public class Tomato extends BmobObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
