package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "favorites",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_user"),
                @ForeignKey(entity = Festival.class,
                        parentColumns = "id",
                        childColumns = "id_festival")
        })
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "id_user")
    public long id_user;

    @ColumnInfo(name = "id_festival")
    public long id_festival;

    public Favorites(long id_user, long id_festival) {
        this.id_user = id_user;
        this.id_festival = id_festival;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getId_user() { return id_user; }

    public void setId_user(long id_user) { this.id_user = id_user; }

    public long getId_festival() { return id_festival; }

    public void setId_festival(long id_festival) { this.id_festival = id_festival; }
}
