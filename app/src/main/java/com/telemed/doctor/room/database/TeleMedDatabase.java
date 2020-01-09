package com.telemed.doctor.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.model.Country;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.room.dao.BasicInfoProfileDao;
import com.telemed.doctor.room.dao.CountryDao;
import com.telemed.doctor.room.dao.GenderDao;
import com.telemed.doctor.room.dao.LanguageDao;
import com.telemed.doctor.room.dao.SpecialityDao;

@Database(entities = {Country.class, Gender.class,
        Language.class, Speciliaty.class, BasicInfoResponse.BasicDetail.class}, version = 1, exportSchema = false)
public abstract class TeleMedDatabase extends RoomDatabase {
    private static TeleMedDatabase mInstance;
    private static final String DATABASE_NAME = "app_database";

    public abstract CountryDao countryDao();
    public abstract LanguageDao languageDao();
    public abstract GenderDao genderDao();
    public abstract SpecialityDao specialityDao();
    public abstract BasicInfoProfileDao basicInfoProfileDao();
    public synchronized static TeleMedDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), TeleMedDatabase.class, DATABASE_NAME)
//                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

}
