package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notification", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "id_user",
        onDelete = CASCADE))
public class Notification {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "datetime")
    public String datetime;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "id_user", index = true)
    public long id_user;

    public Notification(String datetime, String content, long id_user) {

        this.datetime = datetime;
        this.content = content;
        this.id_user = id_user;
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

    public void setDateTime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatetime(String datetime) {this.datetime = datetime; }

    public long getId_user() { return id_user; }

    public void setId_user(long id_user) { this.id_user = id_user; }
}
