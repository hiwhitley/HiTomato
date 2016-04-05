package com.hiwhitley.potatoandtomato.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.hiwhitley.potatoandtomato.base.system.HiTomatoApplication;
import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.fragment.RecyclerListFragment;
import com.hiwhitley.potatoandtomato.util.DoubleClickExitHelper;
import com.hiwhitley.potatoandtomato.util.NotificationHelper;
import com.hiwhitley.potatoandtomato.widget.ColorDialog;
import com.hiwhitley.potatoandtomato.widget.CrossView;
import com.hiwhitley.potatoandtomato.widget.PromptDialog;

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
    private NotificationManager manager;
    private PromptDialog promptDialog;
    private ColorDialog colorDialog;

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

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        promptDialog =  new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setTitleText("Success").setContentText("Your info text goes here. Loremipsum dolor sit amet, consecteturn adipisicing elit, sed do eiusmod.")
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });

        colorDialog = new ColorDialog(this);
        colorDialog.setTitle(getString(R.string.exit_menu_title));
        colorDialog.setContentText(getString(R.string.exit_menu_content));
        colorDialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
        colorDialog.setPositiveListener(getString(R.string.cancel_exit), new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                //Toast.makeText(this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
                colorDialog.dismiss();
            }
        }).setNegativeListener(getString(R.string.exit), new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                finish();
                colorDialog.dismiss();
            }
        });

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
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(getApplicationContext(), MainListActivity.class), 0);
                NotificationHelper.setNotication(getApplicationContext(), manager, pendingIntent);
                promptDialog.show();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        colorDialog.show();
    }
}
