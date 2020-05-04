package com.example.dance_world.database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "user_festival",
        primaryKeys = { "id_user", "id_festival" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_user"),
                @ForeignKey(entity = Festival.class,
                        parentColumns = "id",
                        childColumns = "id_festival")
        })
public class UserFestival {

    @ColumnInfo(name = "id_user")
    public long id_user;

    @ColumnInfo(name = "id_festival" ,index = true)
    public long id_festival;

    public UserFestival(long id_user, long id_festival) {
        this.id_user = id_user;
        this.id_festival = id_festival;
    }
}
