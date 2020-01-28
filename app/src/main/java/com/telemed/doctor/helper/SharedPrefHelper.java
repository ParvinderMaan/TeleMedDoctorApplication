package com.telemed.doctor.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Pmaan on 13/12/17.
 */

public class SharedPrefHelper {
    // Preference Name
    public static final String PREF_APP = "sync_yes_app";
    public final static String KEY_ACCESS_TOKEN = "access_token";
    public final static String KEY_FIRST_NAME = "first_name";
    public final static String KEY_LAST_NAME = "last_name";
    public final static String KEY_PROFILE_PIC = "pro_pic";
    public final static String KEY_SIGN_IN = "is_sign_in";

    private SharedPreferences mSharedPref;





    public SharedPrefHelper(Context context) {
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }



    public String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public  void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public  boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }
//
    public  void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }
//
//    public  Integer read(String key, int defValue) {
//        return mSharedPref.getInt(key, defValue);
//    }
//
//    public  void write(String key, Integer value) {
//        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
//        prefsEditor.putInt(key, value).apply();
//    }
//
//
    public  void clear() {
        mSharedPref.edit().clear().apply();
    }
}
