package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.User;

import java.util.List;

@Dao
public interface FavoritesDAO {
    @Insert
    void insertFavorite(Favorites favorites);

    @Delete
    void deleteFavorites(Favorites favorites);

    @Query("SELECT * FROM favorites")
    List<Favorites> getAll();
}
