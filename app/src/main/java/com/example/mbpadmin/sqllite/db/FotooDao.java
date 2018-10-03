package com.example.mbpadmin.sqllite.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FotooDao {
    @Query("SELECT * FROM fotoo")
    List<FotooEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FotooEntity> fotoo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFotoo(FotooEntity fotooEntity);

    @Query("DELETE FROM fotoo")
    void deleteAll();

    @Query("SELECT * FROM fotoo WHERE judul LIKE :search "+" ORDER BY id DESC LIMIT 1")
    public List<FotooEntity> findCaptionWithJudul(String search);

}
