package com.example.dance_world.database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "dj_festival",
        primaryKeys = { "id_dj", "id_festival" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_dj"),
                @ForeignKey(entity = Festival.class,
                        parentColumns = "id",
                        childColumns = "id_festival")
        })
public class DjFestival {

    @ColumnInfo(name = "id_festival", index = true)
    public long id_festival;

    @ColumnInfo(name = "id_dj", index = true)
    public long id_dj;

    public DjFestival(long id_dj, long id_festival) {
        this.id_dj = id_dj;
        this.id_festival = id_festival;
    }
}
