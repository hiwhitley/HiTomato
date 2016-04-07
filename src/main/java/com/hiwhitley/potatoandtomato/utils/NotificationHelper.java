package com.hiwhitley.potatoandtomato.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;

import com.hiwhitley.potatoandtomato.R;

import java.util.HashMap;

/**
 * Created by hiwhitley on 2016/4/5.
 */
public class NotificationHelper {
    private static final int NOTIFICATION_FLAG = 1;
    private static SoundPool sp = new SoundPool(
            1,              //maxStreams参数，该参数为设置同时能够播放多少音效
            AudioManager.STREAM_MUSIC,  //streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
            0               //srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
    );

    private static HashMap<Integer, Integer> spMap = new HashMap<>();

    public static void setNotication(Context context, NotificationManager manager, PendingIntent pendingIntent) {
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.icon_balance)
                .setContentTitle("HiTomato")
                .setSound(Uri.parse("android.resource://" + context.getPackageName()
                        + "/" + R.raw.alarm))
                .setVibrate(new long[]{0, 500})
                .setContentText("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setTicker("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notification);
    }

    public static void startAlarm(Context context, final int sound, final int number) {    //播放声音,参数sound是播放音效的id，参数number是播放音效的次数

        spMap.put(1, sp.load(context, R.raw.alarm, 1));
        spMap.put(2, sp.load(context, R.raw.alarm, 1));

        AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);//实例化AudioManager对象
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  //返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);//返回当前AudioManager对象的音量值
        final float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sp.play(
                        spMap.get(sound),                   //播放的音乐id
                        volumnRatio,                        //左声道音量
                        volumnRatio,                        //右声道音量
                        1,                                  //优先级，0为最低
                        number,                             //循环次数，0无不循环，-1无永远循环
                        1                                   //回放速度 ，该值在0.5-2.0之间，1为正常速度
                );
            }
        }, 20);

    }
}
