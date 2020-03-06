package com.telemed.doctor;

import android.util.Log;

import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.schedule.view.ScheduleIMonthFragment;

import org.threeten.bp.LocalDate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.HttpException;
import retrofit2.Response;

/*
    removeView it in production .....

    -   -   --  -- - - - - - - - - - -- -


 */
public class JavaCodeTesting {
    private final static String TAG = "RESULT ------------>";
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());

    public static void main(String[] args) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        System.out.println(tz.getDisplayName());
        format.setTimeZone(tz);
        Date d1 = format.parse("2020-03-06 01:00:00");
        System.out.println("PARSED DATE UTC -->"+""+d1);



        TimeZone tzz = TimeZone.getDefault();
        System.out.println(tzz.getDisplayName());
        format.setTimeZone(tzz);
        Date d2 = format.parse("2020-03-06 01:00:00");
        System.out.println("PARSED DATE IST -->"+""+d2);

    }


}
