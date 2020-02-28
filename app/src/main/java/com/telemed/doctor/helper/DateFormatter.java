package com.telemed.doctor.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static String changeTimeFormat12Hour(String time) {
        String formattedTime = "";
        SimpleDateFormat objSimpleDateFormatFROM = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat objSimpleDateFormatTO = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date objDateFrom = objSimpleDateFormatFROM.parse(time);
            formattedTime = objSimpleDateFormatTO.format(objDateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String changeDateFormatText(String date, int day) {
        String formattedDate = "";
        SimpleDateFormat objSimpleDateFormatFROM = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat objSimpleDateFormatTO = new SimpleDateFormat("MMMM dd'th', yyyy", Locale.ENGLISH);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    objSimpleDateFormatTO = new SimpleDateFormat("MMMM d'st', yyyy", Locale.ENGLISH);
                    break;
                case 2:
                    objSimpleDateFormatTO = new SimpleDateFormat("MMMM d'nd', yyyy", Locale.ENGLISH);
                    break;
                case 3:
                    objSimpleDateFormatTO = new SimpleDateFormat("MMMM d'rd', yyyy", Locale.ENGLISH);
                    break;
                default:
                    objSimpleDateFormatTO = new SimpleDateFormat("MMMM d'th', yyyy", Locale.ENGLISH);
                    break;

            }
        else
            objSimpleDateFormatTO = new SimpleDateFormat("MMMM d'th', yyyy", Locale.ENGLISH);


        try {
            Date objDateFrom = objSimpleDateFormatFROM.parse(date);
            formattedDate = objSimpleDateFormatTO.format(objDateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }



}
