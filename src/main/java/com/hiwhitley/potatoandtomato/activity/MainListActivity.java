package com.hiwhitley.potatoandtomato.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.hiwhitley.potatoandtomato.base.system.HiTomatoApplication;
import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.fragment.RecyclerListFragment;
import com.hiwhitley.potatoandtomato.util.DoubleClickExitHelper;
import com.hiwhitley.potatoandtomato.widget.CrossView;

/**
 * Created by hiwhitley on 2016/4/2.
 */
public class MainListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerListFragment mRecyclerListFragment;
    private CrossView mCrossView;
    private TextInputLayout mTextInputLayout;
    private Button commitBtn;
    private int crossViewStatus = CrossView.FLAG_STATE_PLUS;
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
        setListeners();
    }


    private void findViews() {
        toolbar = findView(R.id.tl_custom);
        mDrawerLayout = findView(R.id.dl_left);
        mCrossView = findView(R.id.cv_normal);
        mTextInputLayout = findView(R.id.ti_new_item);
        //mTextInputLayout.setVisibility(View.GONE);
        commitBtn = findView(R.id.btn_commit);
    }

    private void init() {

        mDoubleClickExit = new DoubleClickExitHelper(this);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRecyclerListFragment = new RecyclerListFragment();
        transaction.replace(R.id.id_content, mRecyclerListFragment);
        transaction.commit();

        toolbar.setTitle("");//设置Toolbar标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawerlayout_title, R.string.drawer_open) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("个人中心");//设置Toolbar标题
                toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("");//设置Toolbar标题
                toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setListeners() {
        mCrossView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossViewStatus = mCrossView.toggle();
                showNewItemLayout();
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tomato tomato = new Tomato();
//                tomato.setName("new tomato");
//                tomato.save(getApplicationContext(), new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.i("hiwhitley", "success");
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Log.i("hiwhitley", "fail:" + s);
//                    }
//                });

                Tomato tomato = new Tomato();
                tomato.setName("hello");
                HiTomatoApplication.getDaoSession(getApplicationContext()).getTomatoDao().insert(tomato);
            }
        });
    }

    private void showNewItemLayout() {
        int flag = (crossViewStatus == CrossView.FLAG_STATE_PLUS ? View.GONE : View.VISIBLE);
        mTextInputLayout.setVisibility(flag);
    }

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i("hiwhitley", "onKeyDown");
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
