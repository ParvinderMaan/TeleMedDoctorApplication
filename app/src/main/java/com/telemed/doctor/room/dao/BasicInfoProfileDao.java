package com.telemed.doctor.room.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.telemed.doctor.profile.model.BasicInfoResponse;

@Dao
public interface BasicInfoProfileDao {

    @Query("SELECT * FROM basicinfo")
    LiveData<BasicInfoResponse.BasicDetail> getBasicDetail();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BasicInfoResponse.BasicDetail basicDetail);

    @Update
    void update(BasicInfoResponse.BasicDetail basicDetail);

    @Delete
    void delete(BasicInfoResponse.BasicDetail basicDetail);
}
