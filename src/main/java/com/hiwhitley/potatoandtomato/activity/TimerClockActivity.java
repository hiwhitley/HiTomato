package com.hiwhitley.potatoandtomato.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.bean.DailyEvent;
import com.hiwhitley.potatoandtomato.db.DailyEventDbService;
import com.hiwhitley.potatoandtomato.utils.CalendarUtils;
import com.hiwhitley.potatoandtomato.utils.FontManager;
import com.hiwhitley.potatoandtomato.utils.NotificationHelper;
import com.hiwhitley.potatoandtomato.widget.CircleTimerView;
import com.hiwhitley.potatoandtomato.widget.ColorDialog;
import com.hiwhitley.potatoandtomato.widget.PromptDialog;


/**
 * Created by hiwhitley on 2016/3/30.
 */
public class TimerClockActivity extends AppCompatActivity {

    public static final String TITLE_TEXT = "titleText";
    public static final String TAG = "TimerClockActivity";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Button pauseBtn;
    private Button startBtn;
    private Button backBtn;
    private RadioGroup mRadioGroup;
    private CircleTimerView circleTimerView;
    private TextView titleTextView;
    private PromptDialog promptDialog;
    private ColorDialog colorDialog;

    private  boolean isStart;
    private String titleText;
    private DailyEvent event;

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

        NotificationHelper.init(getApplicationContext());

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.rl_activity_title), iconFont);

        promptDialog = new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                .setTitleText("温馨提示").setContentText("请先设置番茄时间")
                .setAnimationEnable(true)
                .setPositiveListener("好的", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });

        colorDialog = new ColorDialog(this);
        //colorDialog.setTitle("");
        colorDialog.setContentText("先休息一下吧");
        colorDialog.setAnimationEnable(true);
        colorDialog.setPositiveListener("取消", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                //Toast.makeText(this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
                colorDialog.dismiss();
            }
        }).setNegativeListener("完成", new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                finish();
                circleTimerView.pauseTimer();
                colorDialog.dismiss();
            }
        });

        titleText = getIntent().getStringExtra(TITLE_TEXT);
        Log.i("hiwhitley", "titleText:" + titleText);
        titleTextView.setText(titleText);

        isStart = true;

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
                circleTimerView.pauseTimer();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleTimerView.getCurrentTime() == 0) {
                    promptDialog.show();
                    return;
                }
                setPauseStatusViews();
                insertNewDailyEvent();
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
                if(!(TimerClockActivity.this).isFinishing()){
                    setStartStatusView();
                    colorDialog.setTitle(titleText);
                    colorDialog.show();
                    NotificationHelper.startVibrate();
                    NotificationHelper.startAlarm();
                    NotificationHelper.setNotication(getApplicationContext(), null);
                    updateEndTime(event);
                    isStart = true;
                }

            }

            @Override
            public void onTimerStart(int time) {
                Log.i("hiwhitley", "onTimerStart:");
            }

            @Override
            public void onTimerPause(int time) {
                Log.i("hiwhitley", "onTimerPause:");
                isStart = false;
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

    private void updateEndTime(DailyEvent event) {
        event.setEndTime(CalendarUtils.getNowTime(DATE_FORMAT));
        DailyEventDbService.getInstance(getApplicationContext()).insertDailyEvent(event);
    }

    private void insertNewDailyEvent() {
        if (isStart) {
            event = new DailyEvent();
            event.setName(titleText);
            event.setStartTime(CalendarUtils.getNowTime(DATE_FORMAT));
            event.setEndTime("");
            event.setDuration(CalendarUtils.secToTime(circleTimerView.getCurrentTime()));
            Log.i(TAG, event.toString());
            //DailyEventDbService.getInstance(getApplicationContext()).insertDailyEvent(event);
        }
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

        if (circleTimerView.isEnabled())
            circleTimerView.setEnabled(false);
        circleTimerView.startTimer();
        startBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }
}
