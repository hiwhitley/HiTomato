package com.hiwhitley.potatoandtomato.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;

import com.hiwhitley.potatoandtomato.R;

/**
 * Created by hiwhitley on 2016/4/5.
 */
public class NotificationHelper {
    private static final int NOTIFICATION_FLAG = 1;

    public static void setNotication(Context context,NotificationManager manager,PendingIntent pendingIntent){
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.icon_balance)
                .setContentTitle("HiTomato")
                .setSound(Uri.parse("android.resource://" + context.getPackageName()
                        + "/" + R.raw.alarm))
                .setVibrate(new long[]{0,500})
                .setContentText("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setTicker("你已成功完成了一个番茄，赶紧休息一下吧！")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notification);
    }


}
