package com.hiwhitley.potatoandtomato.utils;

import com.hiwhitley.potatoandtomato.base.system.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hiwhitley on 2016/4/15.
 */
public class CalendarUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        return dateFormat.format(now);
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int second = 0;
        int minute = 0;
        if (time < 0)
            timeStr = "0sec";
        else if (time < 60) {
            timeStr = time + " sec";
        } else {
            minute = time / 60;
            second = time % 60;
            timeStr = minute + " min";
        }

        return timeStr;
    }

    /**
     * 将日期信息转换成今天、明天、后天、星期
     *
     * @param date
     * @return
     */
    public static String getDateDetail(String date) {
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            today.setTime(df.parse(getNowTime(DATE_FORMAT)));
            today.set(Calendar.HOUR, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            target.setTime(df.parse(date));
            target.set(Calendar.HOUR, 0);
            target.set(Calendar.MINUTE, 0);
            target.set(Calendar.SECOND, 0);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis();
        int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        return showDateDetail(xcts, target);
    }


    /**
     * 将日期差显示为日期或者星期
     *
     * @param xcts
     * @param target
     * @return
     */
    private static String showDateDetail(int xcts, Calendar target) {
        switch (xcts) {
            case 0:
                return Constants.TODAY;
            case 1:
                return Constants.TOMORROW;
            case 2:
                return Constants.AFTER_TOMORROW;
            case -1:
                return Constants.YESTERDAY;
            case -2:
                return Constants.BEFORE_YESTERDAY;
            default:
                int dayForWeek = 0;
                dayForWeek = target.get(Calendar.DAY_OF_WEEK);
                switch (dayForWeek) {
                    case 1:
                        return Constants.SUNDAY;
                    case 2:
                        return Constants.MONDAY;
                    case 3:
                        return Constants.TUESDAY;
                    case 4:
                        return Constants.WEDNESDAY;
                    case 5:
                        return Constants.THURSDAY;
                    case 6:
                        return Constants.FRIDAY;
                    case 7:
                        return Constants.SATURDAY;
                    default:
                        return null;
                }

        }
    }


    public static String timeAgo(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        long timeStamp = date.getTime();
        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp) / 1000;
        long minutes = Math.abs(seconds / 60);
        long hours = Math.abs(minutes / 60);
        boolean isyesterday = false;
        try {
            isyesterday = isYesterday(date, currentTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isyesterday) {
            return "昨天";
        }
        if (hours < 24) {
            return "今天";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            return dateString;
        }

    }


    private static boolean isYesterday(Date oldTime, Date newTime) throws ParseException {
        if (newTime == null) {
            newTime = new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return true;
        } else if ((today.getTime() - oldTime.getTime()) <= 0) { //至少是今天
            return false;
        } else { //至少是前天
            return false;
        }

    }
}
