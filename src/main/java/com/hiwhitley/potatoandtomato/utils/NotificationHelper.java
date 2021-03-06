package com.hiwhitley.potatoandtomato.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.fragment.SettingFragment;

import java.util.HashMap;

/**
 * Created by hiwhitley on 2016/4/5.
 */
public class NotificationHelper {
    public static final String TAG = "hiwhitley";
    private static final int NOTIFICATION_FLAG = 1;

    private static boolean isSoundOn;
    private static boolean isVibrateOn;
    private static int soundRepeat;
    private static int soundType;

    private static Vibrator vibrator;
    private static float volumnRatio;

    private static SoundPool sp;
    private static HashMap<Integer, Integer> spMap;

    public static void init(Context context) {

        sp = new SoundPool(
                1,              //maxStreams参数，该参数为设置同时能够播放多少音效
                AudioManager.STREAM_MUSIC,  //streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
                0               //srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
        );

        spMap = new HashMap<>();

        spMap.put(0, sp.load(context, R.raw.alarm1, 1));
        spMap.put(1, sp.load(context, R.raw.alarm2, 1));
        spMap.put(2, sp.load(context, R.raw.alarm3, 1));

        isSoundOn = (Boolean) SPUtils.get(context, SettingFragment.IS_SOUND_ON, true);
        Log.i(TAG, "isSoundOn:" + isSoundOn);
        isVibrateOn = (Boolean) SPUtils.get(context, SettingFragment.IS_VIBRATE_ON, true);
        Log.i(TAG, "isVibrateOn:" + isVibrateOn);
        soundType = (int) SPUtils.get(context, SettingFragment.SOUND_TYPE, 0);
        soundRepeat = (int) SPUtils.get(context, SettingFragment.SOUND_REPEAT, 0);
        Log.i(TAG, "soundRepeat:" + soundRepeat);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);//实例化AudioManager对象
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  //返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);//返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
    }

    public static void setNotication(Context context,PendingIntent pendingIntent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.icon_launcher)
                .setContentTitle("Hi番茄")
                //.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm))
                .setVibrate(new long[]{0, 500})
                .setContentText("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setTicker("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notification);

    }


    public static void startVibrate() {
        if (!isVibrateOn)
            return;
        System.out.println("startVibrate");
        vibrator.vibrate(new long[]{100, 400, 100, 400, 100, 400}, -1);
    }

    public static void startAlarm() {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数
        if (!isSoundOn)
            return;
        System.out.println("startAlarm");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sp.play(
                        spMap.get(soundType),                   //播放的音乐id
                        volumnRatio,                        //左声道音量
                        volumnRatio,                        //右声道音量
                        1,                                  //优先级，0为最低
                        soundRepeat,                             //循环次数，0无不循环，-1无永远循环
                        1                                   //回放速度 ，该值在0.5-2.0之间，1为正常速度
                );
            }
        }, 500);
    }
}
