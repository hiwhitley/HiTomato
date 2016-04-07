package com.hiwhitley.potatoandtomato.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by hiwhitley on 2016/4/7.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    protected boolean isCreate = true;

    protected BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (BaseActivity) activity;
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCreate = false;
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 当Fragment可见时调用该方法
     */
    protected void onVisible() {

    }

    /**
     * Fragment不可见时调用
     */
    protected void onInvisible() {
    }


    protected abstract void findViews();

    protected abstract void init();

    protected abstract void setListener();
}
