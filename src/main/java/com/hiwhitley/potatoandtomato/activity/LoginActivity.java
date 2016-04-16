package com.hiwhitley.potatoandtomato.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;


/**
 * Created by hiwhitley on 2016/3/29.
 */
public class LoginActivity extends BaseActivity {

    private Button loginBtn;
    private Button testBtn;
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
        testBtn = findView(R.id.btn_test_login);
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

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                //api.sendReq(req);

            }
        });

    }
}
