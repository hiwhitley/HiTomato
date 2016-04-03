package com.hiwhitley.potatoandtomato.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hiwhitley.potatoandtomato.R;


/**
 * Created by hiwhitley on 2016/3/29.
 */
public class LoginActivity extends BaseActivity{

    private Button loginBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setListeners();
    }

    @Override
    protected void findViews() {
        loginBtn = findView(R.id.sign_in_button);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
