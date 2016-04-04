package com.hiwhitley.potatoandtomato.impl;

import android.view.View;

/**
 * Created by hiwhitley on 2016/4/3.
 */
public interface OnRecyclerViewItemClickListener {
    <T> void onItemClick(View view , T data);
}
