package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dance_world.database.entities.Dj;

import java.util.List;

@Dao
public interface DjDAO {
    @Insert
    void insertDj(Dj dj);

    @Delete
    void deleteDj(Dj dj);

    @Query("SELECT * FROM dj")
    List<Dj> getAll();
}
