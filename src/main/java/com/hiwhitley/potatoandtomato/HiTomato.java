package com.hiwhitley.potatoandtomato;

import android.app.Application;
import cn.bmob.v3.Bmob;

/**
 * Created by hiwhitley on 2016/4/4.
 */
public class HiTomato extends Application{

    private static final String ApplicationID = "这里输入你的Bmob ApplicationID";
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 Bmob SDK
        Bmob.initialize(this, ApplicationID);
    }
}
