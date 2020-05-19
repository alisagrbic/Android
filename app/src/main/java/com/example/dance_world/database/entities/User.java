package com.example.dance_world.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user")
public class User{

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "surname")
    public String surname;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "perimeter")
    public int perimeter;

    @ColumnInfo(name = "loggedIn")
    public boolean loggedIn;

    public User(String name, String surname, String username, String email, String password, int perimeter, boolean loggedIn) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.perimeter = perimeter;
        this.loggedIn = loggedIn;
    }

    //For printing
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", perimeter=" + perimeter +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPerimeter() { return perimeter; }

    public void setPerimeter(int perimeter) { this.perimeter = perimeter; }

    public boolean isLoggedIn() { return loggedIn; }

    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
}
