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

import retrofit2.HttpException;
import retrofit2.Response;

/*
    removeView it in production .....

    -   -   --  -- - - - - - - - - - -- -


 */
public class JavaCodeTesting {
    private final static String TAG = "RESULT ------------>";

    public static void main(String[] args) throws ParseException {
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(minDateSelect.get(Calendar.YEAR), minDateSelect.get(Calendar.MONTH), minDateSelect.getActualMinimum(Calendar.DATE));

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDateSelect.get(Calendar.YEAR), maxDateSelect.get(Calendar.MONTH), maxDateSelect.getActualMaximum(Calendar.DATE));


        Calendar calendarObj = Calendar.getInstance();
        Date todayDate=new Date(calendarObj.getTimeInMillis());
        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        System.out.println("All Dates---------------------->");
        while (i.hasNext()) {
            Date date = i.next();


        }







//        Iterator<Date> ii = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
//
//
//
//        System.out.println("<----------------------------->");
//        while (ii.hasNext()) {
//            Date date = ii.next();
////            if (date.after(calendarObj.getTime())) {
////                System.out.println("AFTER DATE -->"+date);
////            } else {
////                System.out.println("BEFORE DATE -->"+date);
////            }
////            if(calendarObj.getTime().compareTo(date) > 0) {
//////                System.out.println("AFTER DATE -->"+date);
//////            } else if(calendarObj.getTime().compareTo(date) < 0) {
//////                System.out.println("BEFORE DATE -->"+date);
//////            } else if(calendarObj.getTime().compareTo(date) == 0) {
//////                System.out.println("------------TODAY---------"+date);
//////            }
//
//            // Compare the dates
//            if (date.after(calendarObj.getTime())) {
//
//                // When Date d1 > Date d2
//                System.out.println("Date1 is after Date2"+date);
//            }
//
//            else if (date.before(calendarObj.getTime())) {
//
//                // When Date d1 < Date d2
//                System.out.println("Date1 is before Date2"+date);
//            }
//
//            else if (date.equals(calendarObj.getTime())) {
//
//                // When Date d1 = Date d2
//                System.out.println("Date1 is equal to Date2"+date);
//            }
//
//        }



    }

    public static String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
    }
}
