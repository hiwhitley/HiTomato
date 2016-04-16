package com.hiwhitley.potatoandtomato.adapter.viewholer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hiwhitley.potatoandtomato.R;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class StatisticHeaderViewHoler extends RecyclerView.ViewHolder{

    public TextView mTitle;
    public TextView mContent;
    public TextView mDuration;

    public StatisticHeaderViewHoler(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.tv_group_title);
        mContent = (TextView) itemView.findViewById(R.id.tv_item_content);
        mDuration = (TextView) itemView.findViewById(R.id.tv_item_duration);
    }

}
