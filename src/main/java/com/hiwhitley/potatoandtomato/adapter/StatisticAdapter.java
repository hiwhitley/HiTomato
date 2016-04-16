package com.hiwhitley.potatoandtomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.adapter.viewholer.StatisticHeaderViewHoler;
import com.hiwhitley.potatoandtomato.adapter.viewholer.StatisticViewHoler;
import com.hiwhitley.potatoandtomato.bean.DailyEvent;
import com.hiwhitley.potatoandtomato.utils.CalendarUtils;

import java.util.List;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class StatisticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "hiwhitley";
    private static final int GROUP_TITLE = 0;
    private static final int GROUP_ITEM = 1;

    private Context mContext;
    private List<DailyEvent> mDailyEvents;
    private LayoutInflater mInflater;


    public StatisticAdapter(Context context, List<DailyEvent> dailyEvents) {
        this.mContext = context;
        this.mDailyEvents = dailyEvents;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == GROUP_ITEM) {
            return new StatisticViewHoler(mInflater.inflate(R.layout.item_fragment_statistic, parent, false));
        } else {
            return new StatisticHeaderViewHoler(mInflater.inflate(R.layout.item_view_header, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatisticHeaderViewHoler)
            setHeadViewHolder((StatisticHeaderViewHoler) holder, position);
        else
            setItemViewHolder((StatisticViewHoler) holder, position);
    }

    private void setItemViewHolder(StatisticViewHoler holder, int position) {
        holder.mContent.setText(mDailyEvents.get(position).getName());
        holder.mDuration.setText(mDailyEvents.get(position).getDuration());
    }

    private void setHeadViewHolder(StatisticHeaderViewHoler holder, int position) {
        holder.mTitle.setText(CalendarUtils.timeAgo(mDailyEvents.get(position).getStartTime()));
        holder.mContent.setText(mDailyEvents.get(position).getName());
        holder.mDuration.setText(mDailyEvents.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return mDailyEvents.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return GROUP_TITLE;
        DailyEvent priorWeekViewEvent = mDailyEvents.get(position - 1);
        DailyEvent weekViewEvent = mDailyEvents.get(position);
        if (isItem(priorWeekViewEvent, weekViewEvent))
            return GROUP_ITEM;
        else
            return GROUP_TITLE;
    }

    private boolean isItem( DailyEvent priorWeekViewEvent, DailyEvent weekViewEvent){
        String pre = getDateString(priorWeekViewEvent.getStartTime());
        String current = getDateString(weekViewEvent.getStartTime());
        return pre.equals(current);
    }

    private String getDateString(String date){
        return date.substring(0, 11);
    }

}
