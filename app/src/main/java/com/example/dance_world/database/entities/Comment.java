package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "comment", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "id_user",
        onDelete = CASCADE),
        @ForeignKey(entity = Festival.class,
        parentColumns = "id",
        childColumns = "id_festival")})
public class Comment {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "datetime")
    public String datetime;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "id_user", index = true)
    public long id_user;

    @ColumnInfo(name = "id_festival", index = true)
    public long id_festival;

    public Comment(String datetime, String content, long id_user, long id_festival) {
        this.datetime = datetime;
        this.content = content;
        this.id_user = id_user;
        this.id_festival = id_festival;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_festival() {
        return id_festival;
    }

    public void setId_festival(long id_festival) {
        this.id_festival = id_festival;
    }
}
