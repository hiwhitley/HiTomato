package com.hiwhitley.potatoandtomato.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhitley.potatoandtomato.activity.TimerClockActivity;
import com.hiwhitley.potatoandtomato.adapter.RecyclerListAdapter;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.helper.OnStartDragListener;
import com.hiwhitley.potatoandtomato.helper.SimpleItemTouchHelperCallback;
import com.hiwhitley.potatoandtomato.impl.OnRecyclerViewItemClickListener;

/**
 * Created by hiwhitley on 2016/4/3.
 */
public class RecyclerListFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private  RecyclerListAdapter adapter;
    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerListAdapter(getActivity(), this);
        //参数view即为我们在onCreateView中return的view
        RecyclerView recyclerView = (RecyclerView) view;
        //固定recyclerview大小
        recyclerView.setHasFixedSize(true);
        //设置adapter
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public <T> void onItemClick(View view, T data) {

                Intent intent = new Intent(getActivity(), TimerClockActivity.class);
                intent.putExtra(TimerClockActivity.TITLE_TEXT, ((Tomato)data).getContent());
                startActivity(intent);
            }

        });


        //设置布局类型为LinearLayoutManager，相当于ListView的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //关联ItemTouchHelper和RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void inSertItem(Tomato tomato){
        adapter.addItem(tomato);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }
}
