package com.telemed.doctor.helper;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Pmaan on 19/7/18.
 *         Copyright © All rights reserved.
 */

public class TimeUtil {
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static DateFormat timeFormat = new SimpleDateFormat("h:mma", Locale.US);
    private static Date date = new Date();
    public static final long now= System.currentTimeMillis();
    public static Long getTimeStamp() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static int getAgeFromDob(String dateTime) {

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            Date currentDate = new Date();
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);

            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH);
            int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

            int deltaYear = currentYear - year;
            int deltaMonth = currentMonth - month;
            int deltaDay = currentDay - day;

            if (deltaYear > 0) {
                if (deltaMonth < 0) {
                    deltaYear--;
                } else if (deltaDay < 0) {
                    deltaYear--;
                }

                return deltaYear;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public  static String getTime(Long x){
        date.setTime(x);
        return timeFormat.format(date);
    }

    public static String getDate(Long x) {
        date.setTime(x);
        return dateFormat.format(date);
    }


    public static String calculateTime(String givenDateString) throws ParseException {
        // make it static ;

        Date mDate=null;

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        mDate = sdf.parse(getLocalTimeStr(givenDateString));
//        System.out.println("Date in milli :: " + timeInMilliseconds);

        long diffInMillisec= 0;
        if (mDate != null) {
            diffInMillisec =  now- mDate.getTime();
        }
        long diffInWeeks;
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec);
        long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);



        if(diffInDays>6){
            System.out.println(" ------------ "+diffInDays +" days ago");
            diffInWeeks=diffInDays/7;
            return diffInWeeks +" w ago";
        }


        if(diffInDays>0){
            System.out.println(" ------------ "+diffInDays +" days ago");
            return diffInDays +" d ago";
        }
        if(diffInHours>0){
            System.out.println(" ------------ "+diffInHours +" hours ago");
            return diffInHours +" h ago";
        }
        if(diffInMin>0){
            System.out.println(" ------------ "+diffInMin +" min ago");
            return diffInMin +" m ago";
        }

        if(diffInSec>5){ // 2 ,3 second laiyta Now
            System.out.println(" ------------ "+diffInSec +" sec ago");
            return diffInSec +" s ago";
        }else {
            System.out.println(" ------------ "+diffInSec +" Now");
            return " now";
        }

    }

    public static String getLocalTimeStr(String strTime)
    {
        String givenDate = null;
        long timeInMillisec = 0;

        TimeZone tz = TimeZone.getTimeZone("UTC");
        System.out.println(tz.getDisplayName());


        Date d4  = null;
        format.setTimeZone(tz);
//        Log.e("WITHOUT FORMAT DATE> ",""+strTime);
        Date d1 = null;
        Date d2 = new Date();
//        Log.e("Current Date >> ","d2   >>>  "+d2);

        try {
            Log.d("DATE","date before parse "+strTime);
            d1 = format.parse(strTime);
            Log.e("PARSED DATE",""+d1);
            Date d3 = new Date(d1.getTime()+ TimeZone.getDefault().getOffset(d2.getTime()));
            Log.e("D3 >>> "," "+d3);
            String[] dateArray = d3.toString().split(" ");
            String dateStr = dateArray[0] +" " + dateArray[1] + " " + dateArray[2] + " " + dateArray[3] + " " + dateArray[5];
//            "Tue Jul 26 12:23:32 GMT+05:30 2016
            SimpleDateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
//            Date d3 = format.parse(zoneformat.format(d2));
            d4 = format1.parse(dateStr);
            Log.e("d4 >>> ",""+d4);
            givenDate = format.format(d4);

//            Log.e("Date Given","d1   >>>   "+givenDate);
//            Log.e("Current Date >> ","d2   >>>  "+d2);

            timeInMillisec = d4.getTime();
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
        return givenDate;

    }

    public static int getDaysRemaining(String createdOn) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date strDate = null;

        strDate = sdf.parse(createdOn);
        long  diff= System.currentTimeMillis() - strDate.getTime();

        return (int) (diff / (1000*60*60*24));

    }
    public static long daysBetween(Long one, Long two) {
        long difference = (one-two)/86400000;
        return Math.abs(difference);
    }


    public static Date dateFromUTC(Date date){
        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }

    public static Date dateToUTC(Date date){
        return new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }





}
