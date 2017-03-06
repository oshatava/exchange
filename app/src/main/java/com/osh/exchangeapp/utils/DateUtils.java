package com.osh.exchangeapp.utils;

import android.content.Context;

import com.osh.exchangeapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oleg on 3/6/2017.
 */

public class DateUtils {
    public static String formatDate(Context context, long date){
        //String dateFormat = context.getResources().getString(R.string.date_format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat format = new SimpleDateFormat();
        String ret = format.format(calendar.getTime());
        return ret;
    }
}
