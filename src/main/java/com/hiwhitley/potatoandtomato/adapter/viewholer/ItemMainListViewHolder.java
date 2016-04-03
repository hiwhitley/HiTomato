package com.hiwhitley.potatoandtomato.adapter.viewholer;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.helper.ItemTouchHelperViewHolder;

/**
 * Created by hiwhitley on 2016/4/3.
 */
public class ItemMainListViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
    public TextView text;
    public ImageView handle;
    public ItemMainListViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
        handle = (ImageView) itemView.findViewById(R.id.handle);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
