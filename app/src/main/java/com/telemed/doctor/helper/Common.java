package com.telemed.doctor.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Common {


    public static boolean isNetworkAvail(Context applicationContext) throws NullPointerException {
        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public static String getDate(String dateStr) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat toDf = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

        Date date = null;
        date = df.parse(dateStr);
        String formattedDate = toDf.format(date);
        return formattedDate.toUpperCase();
    }

    public static int getConvertToYearInt(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+0000", Locale.ENGLISH);
        SimpleDateFormat toDf = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        toDf.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));

        Date date = null;
        try {
            date = df.parse(dateStr);
            String formattedDate = toDf.format(date);
            if (Integer.parseInt(formattedDate) < 10) {
                formattedDate = "0" + formattedDate;
            }
            return Integer.parseInt(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getConvertToMonthHomeInt(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+0000", Locale.ENGLISH);
        SimpleDateFormat toDf = new SimpleDateFormat("MM", Locale.ENGLISH);

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        toDf.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));

        Date date = null;
        try {
            date = df.parse(dateStr);
            String formattedDate = toDf.format(date);
            return Integer.parseInt(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getConvertToDayInt(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+0000", Locale.ENGLISH);
        SimpleDateFormat toDf = new SimpleDateFormat("dd", Locale.ENGLISH);

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        toDf.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));

        Date date = null;
        try {
            date = df.parse(dateStr);
            String formattedDate = toDf.format(date);
            if (Integer.parseInt(formattedDate) < 10) {
                formattedDate = "0" + formattedDate;
            }
            return Integer.parseInt(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
