package com.hiwhitley.potatoandtomato.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hiwhitley on 2016/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected abstract void findViews();

    protected abstract void init();

    protected abstract void setListeners();

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }
}
