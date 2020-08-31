package com.example.dance_world.database.dao;

import com.example.dance_world.database.entities.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user WHERE user.username == :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user WHERE user.`loggedIn` == :state")
    User getLoggedInUser(boolean state);

    @Query("SELECT * FROM user WHERE user.id == :id")
    User getUserById(Long id);

}
