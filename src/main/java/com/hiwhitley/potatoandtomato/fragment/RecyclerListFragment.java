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

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.activity.TimerClockActivity;
import com.hiwhitley.potatoandtomato.adapter.RecyclerListAdapter;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.helper.OnStartDragListener;
import com.hiwhitley.potatoandtomato.helper.SimpleItemTouchHelperCallback;
import com.hiwhitley.potatoandtomato.impl.OnRecyclerViewItemClickListener;
import com.hiwhitley.potatoandtomato.widget.EmptyRecyclerView;

/**
 * Created by hiwhitley on 2016/4/3.
 */
public class RecyclerListFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private  RecyclerListAdapter adapter;
    private View mRootView;
    private EmptyRecyclerView mEmptyRecyclerView;
    private View mEmptyView;
    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_main, container, false);
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

    private void findViews() {
        mEmptyRecyclerView = (EmptyRecyclerView) mRootView.findViewById(R.id.rv_main);
        mEmptyView = mRootView.findViewById(R.id.empty_view);
    }

    private void init() {
    }

    private void setListener() {
        adapter = new RecyclerListAdapter(getActivity(), this);
        //设置adapter
        mEmptyRecyclerView.setEmptyView(mEmptyView);
        mEmptyRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public <T> void onItemClick(View view, T data) {

                Intent intent = new Intent(getActivity(), TimerClockActivity.class);
                intent.putExtra(TimerClockActivity.TITLE_TEXT, ((Tomato)data).getContent());
                startActivity(intent);
            }

        });


        //设置布局类型为LinearLayoutManager，相当于ListView的样式
        mEmptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //关联ItemTouchHelper和RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mEmptyRecyclerView);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void insertItem(Tomato tomato){
        adapter.addItem(tomato);
        // adapter.notifyDataSetChanged();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }
}
