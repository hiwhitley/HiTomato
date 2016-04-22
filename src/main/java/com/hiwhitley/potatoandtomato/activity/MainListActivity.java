package com.hiwhitley.potatoandtomato.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseActivity;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.db.TomatoDbService;
import com.hiwhitley.potatoandtomato.fragment.HistoryFragment2;
import com.hiwhitley.potatoandtomato.fragment.RecyclerListFragment;
import com.hiwhitley.potatoandtomato.fragment.SettingFragment;
import com.hiwhitley.potatoandtomato.fragment.StatisticFragment;
import com.hiwhitley.potatoandtomato.utils.FontManager;
import com.hiwhitley.potatoandtomato.utils.LogUtils;
import com.hiwhitley.potatoandtomato.widget.ColorDialog;
import com.hiwhitley.potatoandtomato.widget.CrossView;
import com.hiwhitley.potatoandtomato.widget.PromptDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by hiwhitley on 2016/4/2.
 */
public class MainListActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerListFragment mRecyclerListFragment;
    private CrossView mCrossView;
    private Button commitBtn;
    private int crossViewStatus = CrossView.FLAG_STATE_PLUS;
    private NotificationManager manager;
    private PromptDialog promptDialog;
    private ColorDialog colorDialog;
    private MaterialEditText materialEditText;
    private LinearLayout mNewItemLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
        setListeners();
    }


    protected void findViews() {
        toolbar = findView(R.id.tl_custom);
        mDrawerLayout = findView(R.id.dl_left);
        mCrossView = findView(R.id.cv_normal);
        materialEditText = findView(R.id.met_new_tomato);
        mNewItemLayout = findView(R.id.ll_new_tomato);
        commitBtn = findView(R.id.btn_arrow_right);
        mNavigationView = findView(R.id.nav_menu);
    }

    protected void init() {

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.ll_new_tomato), iconFont);


        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        promptDialog = new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setTitleText(getString(R.string.exit_menu_title)).setContentText(getString(R.string.exit_menu_content))
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });

        colorDialog = new ColorDialog(this);
        colorDialog.setTitle(getString(R.string.exit_menu_title));
        colorDialog.setContentText(getString(R.string.exit_menu_content));
        colorDialog.setPositiveListener(getString(R.string.cancel_exit), new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                //Toast.makeText(this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setNegativeListener(getString(R.string.exit), new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                finish();
                dialog.dismiss();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRecyclerListFragment = new RecyclerListFragment();
        transaction.replace(R.id.id_content, mRecyclerListFragment);
        transaction.commit();


        toolbar.setTitle("首页");//设置Toolbar标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawerlayout_title, R.string.drawer_open) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    protected void setListeners() {
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
                addNewTomato();

            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                navigationViewItemSelected(item);
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void startNextView(Fragment fragment, String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.id_content, fragment).commit();
        toolbar.setTitle(title);
    }

    private void navigationViewItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startNextView(new RecyclerListFragment(), "首页");
                mCrossView.setVisibility(View.VISIBLE);
                Log.i("hiwhitley", "nav_home");
                break;
            case R.id.nav_history:
                startNextView(new StatisticFragment(), "历史统计");
                mCrossView.setVisibility(View.GONE);
                break;
            case R.id.nav_setting:
                startNextView(new SettingFragment(), "基本设置");
                mCrossView.setVisibility(View.GONE);
                break;
            case R.id.nav_upload:

                List<BmobObject> persons = new ArrayList<BmobObject>();
                List<Tomato> tomatos = TomatoDbService.getInstance(this).loadAllTomato();
                for (Tomato tomato : tomatos) {
                    persons.add(tomato);
                }
                new BmobObject().insertBatch(this, persons, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        LogUtils.i("批量添加成功");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        LogUtils.i("批量添加失败:" + msg);
                    }
                });
                break;
            case R.id.nav_love:

                startNextView(new HistoryFragment2(), "历史统计");
                mCrossView.setVisibility(View.GONE);
                break;
            case R.id.nav_email:
                startNextView(new StatisticFragment(), "联系我");
                mCrossView.setVisibility(View.GONE);
                break;
            case R.id.nav_about:
                break;
        }
    }

    private void addNewTomato() {
        if (TextUtils.isEmpty(materialEditText.getText().toString()) || materialEditText.getText().length() > 32) {
            materialEditText.setError("请正确输入番茄内容");
            return;
        }
        Tomato tomato = new Tomato();
        tomato.setContent(materialEditText.getText().toString());
        tomato.setOrder(TomatoDbService.getInstance(getApplicationContext()).getTomatoMaxOrder() + 1);
        tomato.setTime("today");
        TomatoDbService.getInstance(getApplicationContext()).insertTomato(tomato);
        mRecyclerListFragment.inSertItem(tomato);
        mCrossView.plus();
        crossViewStatus = CrossView.FLAG_STATE_PLUS;
        showNewItemLayout();
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(materialEditText.getWindowToken(), 0);
    }

    private void showNewItemLayout() {
        int flag = (crossViewStatus == CrossView.FLAG_STATE_PLUS ? View.GONE : View.VISIBLE);
        materialEditText.setText("");
        mNewItemLayout.setVisibility(flag);
        hideSoftKeyBoard();
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
