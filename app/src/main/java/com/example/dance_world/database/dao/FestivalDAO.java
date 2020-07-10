package com.example.dance_world.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;

import java.util.List;

@Dao
public interface FestivalDAO {
    @Insert
    void insertFestival(Festival festival);

    @Delete
    void deleteFestival(Festival festival);

    @Update
    void updateFestival(Festival festival);

    @Query("SELECT * FROM festival")
    List<Festival> getAll();

    @Query("SELECT name FROM festival")
    String[] getAllNames();

    @Query("SELECT imagePath FROM festival")
    int[] getAllImages();

    @Query("SELECT id FROM festival WHERE festival.name == :name")
    long getIdFestivalByName(String name);

    @Query("SELECT * FROM festival WHERE festival.name == :name")
    Festival getFestivalByName(String name);

    @Query("SELECT * FROM festival WHERE festival.id == :id")
    Festival getFestivalById(long id);

    @Query("SELECT * FROM festival WHERE festival.city == :city")
    Festival getFestivalByCity(String city);
}
