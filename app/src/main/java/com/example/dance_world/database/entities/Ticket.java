package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ticket", foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "id_user",
        onDelete = CASCADE),
        @ForeignKey(entity = Festival.class,
        parentColumns = "id",
        childColumns = "id_festival",
        onDelete = CASCADE)})
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "id_festival", index = true)
    public long id_festival;

    @ColumnInfo(name = "id_user",index = true)
    public long id_user;

    public Ticket(long id, double price, long id_festival, long id_user) {
        this.id = id;
        this.price = price;
        this.id_festival = id_festival;
        this.id_user = id_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId_festival() { return id_festival; }

    public void setId_festival(long id_festival) { this.id_festival = id_festival; }

    public long getId_user() { return id_user; }

    public void setId_user(long id_user) { this.id_user = id_user; }
}
