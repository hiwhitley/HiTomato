package com.hiwhitley.potatoandtomato.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.view.CircleTimerView;


/**
 * Created by hiwhitley on 2016/3/30.
 */
public class TimerClockActivity extends AppCompatActivity {

    public static final String TITLE_TEXT = "titleText";
    private Button pauseBtn;
    private Button startBtn;
    private ImageButton backBtn;

    private RadioGroup mRadioGroup;
    private CircleTimerView circleTimerView;
    private TextView titleTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_clock);
        findViews();
        init();
        setListeners();
    }


    protected void findViews() {

        startBtn = findView(R.id.btn_start);
        pauseBtn = findView(R.id.btn_pause);
        backBtn = findView(R.id.imgBtn_back);

        mRadioGroup = findView(R.id.rp_select_time);
        circleTimerView = findView(R.id.ctv);
        titleTextView = findView(R.id.tv_title);
    }


    protected void init() {

        String titleText = getIntent().getStringExtra(TITLE_TEXT);
        Log.i("hiwhitley","titleText:"+titleText);
        titleTextView.setText(titleText);

    }


    protected void setListeners() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton childRb = findView(checkedId);
                Log.i("hiwhitley", "onCheckedChanged:" + childRb);
                if (null != childRb && childRb.isChecked())
                    circleTimerView.setCurrentTime(Integer.valueOf(childRb.getText().toString()) * 60);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPauseStatusViews();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartStatusView();
            }
        });


        circleTimerView.setCircleTimerListener(new CircleTimerView.CircleTimerListener() {
            @Override
            public void onTimerStop() {
                Log.i("hiwhitley", "onTimerStop:");
                setStartStatusView();
            }

            @Override
            public void onTimerStart(int time) {
                Log.i("hiwhitley", "onTimerStart:");
            }

            @Override
            public void onTimerPause(int time) {
                Log.i("hiwhitley", "onTimerPause:");
            }

            @Override
            public void onTimerTimingValueChanged(int time) {
                Log.i("hiwhitley", "onTimerTimingValueChanged:");

            }

            @Override
            public void onTimerSetValueChanged(int time) {
                Log.i("hiwhitley", "onTimerSetValueChanged:" + time);
                if (time != 15 * 60 && time != 30 * 60 && time != 45 * 60)
                    mRadioGroup.clearCheck();
            }

            @Override
            public void onTimerSetValueChange(int time) {

            }

        });
    }

    private void setStartStatusView() {
        if (!circleTimerView.isEnabled())
            circleTimerView.setEnabled(true);
        circleTimerView.pauseTimer();
        mRadioGroup.setVisibility(View.VISIBLE);
        pauseBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
    }

    private void setPauseStatusViews() {
        if (circleTimerView.getCurrentTime() == 0){
            Toast.makeText(this,"请先设置充电时间！",Toast.LENGTH_SHORT).show();
            return;
        }

        if (circleTimerView.isEnabled())
            circleTimerView.setEnabled(false);
        circleTimerView.startTimer();
        startBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.INVISIBLE);
    }

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }
}
