package com.example.dance_world.database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "artist_festival",
        primaryKeys = { "id_artist", "id_festival" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_artist"),
                @ForeignKey(entity = Festival.class,
                        parentColumns = "id",
                        childColumns = "id_festival")
        })
public class ArtistFestival {

    @ColumnInfo(name = "id_festival", index = true)
    public long id_festival;

    @ColumnInfo(name = "id_artist", index = true)
    public long id_artist;

    public ArtistFestival(long id_artist, long id_festival) {
        this.id_artist = id_artist;
        this.id_festival = id_festival;
    }
}
