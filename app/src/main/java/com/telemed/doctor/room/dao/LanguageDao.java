package com.telemed.doctor.room.dao;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.Speciliaty;

import java.util.List;

@Dao
public interface LanguageDao {


    @Query("SELECT * FROM language")
    LiveData<List<Language>> getAllLanguage();

    @Insert
    void insertAll(List<Language> languageList);

    @Delete
    void delete(Language... mLanguageList);
}
