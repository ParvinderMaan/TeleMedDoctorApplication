package com.telemed.doctor;

import com.telemed.doctor.helper.Validator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.HttpException;
import retrofit2.Response;

/*
    removeView it in production .....

    -   -   --  -- - - - - - - - - - -- -


 */
public class JavaCodeTesting {

    public static void main(String[] args) {

//        try {
//            System.out.println(formatDate("2020-2-2"));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        String oldDate="2020-03-06";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy", Locale.getDefault()).parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd MMMM yyyy",Locale.getDefault());
        String date = simpleDateFormat.format(date1);
        System.out.println(date);

    }


    public static String formatDate(String oldDate) throws ParseException {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = sdFormat.parse(oldDate);
        return sdFormat.format(freshDate);
    }
}
