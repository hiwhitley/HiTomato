package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.adapter.StatisticAdapter;
import com.hiwhitley.potatoandtomato.base.BaseFragment;
import com.hiwhitley.potatoandtomato.base.system.Constants;
import com.hiwhitley.potatoandtomato.bean.DailyEvent;
import com.hiwhitley.potatoandtomato.db.DailyEventDbService;
import com.hiwhitley.potatoandtomato.widget.EmptyRecyclerView;
import com.hiwhitley.potatoandtomato.widget.animation.MoveAnimation;

import java.util.List;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class StatisticFragment extends BaseFragment{

    private View mRootView;
    private View mEmptyView;
    private EmptyRecyclerView mRecyclerView;
    private List<DailyEvent> mDailyEvents;
    private StatisticAdapter mStatisticAdapter;


    public StatisticFragment(){

    }

    public static StatisticFragment newInstance(){
        StatisticFragment f = new StatisticFragment();
        return f;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, Constants.DURATION);
    }

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
        mEmptyView = findView(mRootView, R.id.empty_view);
        mRecyclerView = findView(mRootView, R.id.rv_statistic);
    }

    @Override
    protected void init() {
        mDailyEvents = DailyEventDbService.getInstance(getActivity()).loadAllDailyEvent();
        mStatisticAdapter = new StatisticAdapter(getActivity(), mDailyEvents);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setListener() {
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mStatisticAdapter);
    }
}
