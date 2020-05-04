package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "festival")
public class Festival {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name="gps_longitude")
    public double gps_longitude;

    @ColumnInfo(name="gps_latitude")
    public double gps_latitude;

    @ColumnInfo(name = "comments")
    public String comments;

    @ColumnInfo(name = "datetime")
    public String datetime;

    @ColumnInfo(name = "id_user", index = true)
    public long id_user;

    public Festival(long id, String name, String city, double gps_longitude, double gps_latitude, String comments, String datetime, long id_user) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.gps_longitude = gps_longitude;
        this.gps_latitude = gps_latitude;
        this.comments = comments;
        this.datetime = datetime;
        this.id_user = id_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getGps_longitude() {
        return gps_longitude;
    }

    public void setGps_longitude(double gps_longitude) {
        this.gps_longitude = gps_longitude;
    }

    public double getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(double gps_latitude) {
        this.gps_latitude = gps_latitude;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getId_user() { return id_user; }

    public void setId_user(long id_user) { this.id_user = id_user; }
}
