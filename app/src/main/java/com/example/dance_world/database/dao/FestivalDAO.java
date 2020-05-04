package com.example.dance_world.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dance_world.database.entities.Festival;

import java.util.List;

@Dao
public interface FestivalDAO {
    @Insert
    void insertFestival(Festival festival);

    @Delete
    void deleteFestival(Festival festival);

    @Query("SELECT * FROM festival")
    List<Festival> getAll();
}
