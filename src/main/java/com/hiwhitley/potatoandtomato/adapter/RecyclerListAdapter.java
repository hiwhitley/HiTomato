package com.hiwhitley.potatoandtomato.adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.adapter.viewholer.ItemMainListViewHolder;
import com.hiwhitley.potatoandtomato.helper.ItemTouchHelperAdapter;
import com.hiwhitley.potatoandtomato.helper.OnStartDragListener;
import com.hiwhitley.potatoandtomato.impl.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hiwhitley on 2016/4/3.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<ItemMainListViewHolder> implements ItemTouchHelperAdapter ,View.OnClickListener{


    private OnStartDragListener mStartDragListener;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<String> mItems;

    public RecyclerListAdapter(Context context,OnStartDragListener startDragListener) {
        mItems = new ArrayList<>();
        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
        mStartDragListener = startDragListener;
    }

    @Override
    public ItemMainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list,null);
        view.setOnClickListener(this);
        return new ItemMainListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemMainListViewHolder holder, int position) {

        holder.text.setText(mItems.get(position));
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //如果按下
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    //回调RecyclerListFragment中的startDrag方法
                    //让mItemTouchHelper执行拖拽操作
                    mStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.itemView.setTag(mItems.get(position));
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //交换mItems数据的位置
        Collections.swap(mItems, fromPosition, toPosition);
        //交换RecyclerView列表中item的位置
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        //删除mItems数据
        mItems.remove(position);
        //删除RecyclerView列表对应item
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}