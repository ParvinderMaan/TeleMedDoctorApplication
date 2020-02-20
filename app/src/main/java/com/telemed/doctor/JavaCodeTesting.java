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

        try {
            System.out.println(formatDate("2020-2-2"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static String formatDate(String oldDate) throws ParseException {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = sdFormat.parse(oldDate);
        return sdFormat.format(freshDate);
    }
}
