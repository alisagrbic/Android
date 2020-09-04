package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dj")
public class Dj {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "surname")
    public String surname;

    @ColumnInfo(name = "id_festival", index = true)
    public long id_festival;

    public Dj()  {}

    public Dj(String name, String surname, long id_festival) {
        this.name = name;
        this.surname = surname;
        this.id_festival=id_festival;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId_festival() { return id_festival; }

    public void setId_festival(long id_festival) { this.id_festival = id_festival; }
}
