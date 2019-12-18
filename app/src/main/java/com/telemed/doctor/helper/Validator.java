package com.telemed.doctor.helper;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator  {


    // check email validation
    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // check alphanumeric validation
    public static boolean isAlphaNumeric(String password){
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
    // check OnlyStrings
    public static boolean isOnlyString(String str) {
        return ((str != null)) && (!str.equals("")
                && (str.matches("^[a-zA-Z]*$")));
    }
}
