package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.adapter.StatisticAdapter;
import com.hiwhitley.potatoandtomato.base.BaseFragment;
import com.hiwhitley.potatoandtomato.bean.DailyEvent;
import com.hiwhitley.potatoandtomato.db.DailyEventDbService;
import com.hiwhitley.potatoandtomato.widget.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class StatisticFragment extends BaseFragment{

    private View mRootView;
    private RecyclerView mRecyclerView;
    private ArrayList<WeekViewEvent> mWeekViewEvents;
    private List<DailyEvent> mDailyEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_statistic, container, false);
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
        mRecyclerView = findView(mRootView, R.id.rv_statistic);
    }

    @Override
    protected void init() {
        mDailyEvents = DailyEventDbService.getInstance(getActivity()).loadAllDailyEvent();
        StatisticAdapter mStatisticAdapter = new StatisticAdapter(getActivity(), mDailyEvents);
        mRecyclerView.setAdapter(mStatisticAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setListener() {

    }
}
