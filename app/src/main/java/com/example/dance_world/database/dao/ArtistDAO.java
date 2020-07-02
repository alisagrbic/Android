package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dance_world.database.entities.Artist;

import java.util.List;

@Dao
public interface ArtistDAO {
    @Insert
    void insertArtist(Artist artist);

    @Delete
    void deleteArtist(Artist artist);

    @Query("SELECT * FROM artist")
    List<Artist> getAll();

    @Query("SELECT * FROM artist WHERE artist.id_festival == :festivalId")
    List<Artist> getArtistByFestivalId(Long festivalId);
}
