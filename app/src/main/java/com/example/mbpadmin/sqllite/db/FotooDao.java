package com.example.mbpadmin.sqllite.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FotooDao {
    @Query("SELECT * FROM fotoo")
    List<Fotoo> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FotooEntity> fotoo);
}
