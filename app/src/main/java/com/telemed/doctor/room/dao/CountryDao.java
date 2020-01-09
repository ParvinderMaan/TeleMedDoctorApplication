package com.telemed.doctor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.telemed.doctor.profile.model.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM country")
    LiveData<List<Country>> getAllCountries();

    @Insert
    void insertAll(List<Country> countryList);

    @Delete
    void delete(Country... mCountryList);



}
