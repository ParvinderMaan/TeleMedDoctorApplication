package com.telemed.doctor.room.dao;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Speciliaty;

import java.util.List;

@Dao
public interface SpecialityDao {

    @Query("SELECT * FROM speciality")
    LiveData<List<Speciliaty>> getAllSpeciality();

    @Insert
    void insertAll(List<Speciliaty> speciliatyList);

    @Delete
    void delete(Speciliaty... mSpeciliatyList);
}
