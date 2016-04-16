package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseFragment;
import com.hiwhitley.potatoandtomato.widget.weekview.MonthLoader;
import com.hiwhitley.potatoandtomato.widget.weekview.WeekView;
import com.hiwhitley.potatoandtomato.widget.weekview.WeekViewEvent;
import com.hiwhitley.potatoandtomato.widget.weekview.WeekViewLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hiwhitley on 2016/4/10.
 */
public class HistoryFragment2 extends BaseFragment implements MonthLoader.MonthChangeListener{

    private View mRootView;
    private WeekView mWeekView;
    private List<WeekViewEvent> mWeekViewEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_history2, container, false);
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
        mWeekView = findView(mRootView, R.id.weekView);
    }

    @Override
    protected void init() {
        mWeekView.setMonthChangeListener(this);
        mWeekViewEvents = new ArrayList<>();
        WeekViewEvent event = new WeekViewEvent(1, "hello", 2016, 4, 12, 7, 30, 2016, 4, 12, 8, 50);
        //event.setDayOfWeek(Calendar.MONDAY);
        mWeekViewEvents.add(event);



        mWeekView.setWeekViewLoader(new WeekViewLoader() {
            @Override
            public double toWeekViewPeriodIndex(Calendar instance) {
                return 0;
            }

            @Override
            public List<? extends WeekViewEvent> onLoad(int periodIndex) {
                return mWeekViewEvents;
            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }
}
