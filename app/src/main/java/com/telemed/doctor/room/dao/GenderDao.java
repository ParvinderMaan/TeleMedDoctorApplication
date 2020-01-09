package com.telemed.doctor.room.dao;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.telemed.doctor.profile.model.Country;
import com.telemed.doctor.profile.model.Gender;

import java.util.List;

@Dao
public interface GenderDao {

    @Query("SELECT * FROM gender")
    LiveData<List<Gender>> getAllGender();

    @Insert
    void insertAll(List<Gender> genders);

    @Delete
    void delete(Gender... mGenderList);

}
