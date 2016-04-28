package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseFragment;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class AboutFragment extends BaseFragment{

    private View mRootView;

    public AboutFragment(){

    }

    public static AboutFragment newInstance(){
        AboutFragment f = new AboutFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_about, container, false);
            findViews();
            init();
            setListener();
        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setListener() {

    }
}
