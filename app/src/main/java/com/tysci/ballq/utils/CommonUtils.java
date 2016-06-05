package com.tysci.ballq.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tysci.ballq.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/5/31.
 */
public class CommonUtils {

    public static DisplayMetrics getScreenDisplayMetrics(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static String getDateAndTimeFormatString(long times){
        return new SimpleDateFormat("MM-dd HH:mm").format(new Date(times));
    }

    public static String getDateAndTimeFormatString(Date times){
        return new SimpleDateFormat("MM-dd HH:mm").format(times);
    }

    public static String getMMddString(Date date){
        return new SimpleDateFormat("MM/dd HH:mm").format(date);
    }

    public static String getMM_ddString(Date date){
        return new SimpleDateFormat("MM-dd").format(date);
    }

    public static String getMM_ddString(long date){
        return new SimpleDateFormat("MM-dd").format(new Date(date));
    }


    public static String getTimeOfDay(long times){
        return new SimpleDateFormat("HH:mm").format(new Date(times));
    }


    public static String getTimeOfDay(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }

    public static Date getDifferenceDaysDate(long times, int diffDay){
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        d.setTime(times);
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE)+diffDay);
        return now.getTime();
    }

    public static String getYYMMdd(Date date){
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public static Date getDateAndTimeFromGMT(String GMTDate){
        if (GMTDate.length() > 20)
        {
            GMTDate = GMTDate.substring(0, 19) + "Z";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        try {
            final Date d = sdf.parse(GMTDate);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeFormatName(long times){
        String timeName=null;
        //KLog.e("当前日期:" +getFormatDateString(times));
        long currentTimes=System.currentTimeMillis()/1000;
        times/=1000;
        long offsetTimes=currentTimes-times;
        if(offsetTimes<60){
            timeName="刚刚";
        }else if(offsetTimes<60*60){
            timeName=offsetTimes/60+"分钟前";
        }else if(offsetTimes<=60*60*8){
            timeName=offsetTimes/(60*60)+"小时前";
        }else if(offsetTimes<60*60*24*2){
            timeName="1天前";
        }else if(offsetTimes<60*60*24*4){
            //KLog.e("offsetTimes:"+offsetTimes);
            timeName=offsetTimes/(60*60*24)+"天前";
        }else{
            timeName=getMM_ddString(times*1000);
        }
        return timeName;
    }


}
