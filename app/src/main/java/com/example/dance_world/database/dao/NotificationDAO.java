package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dance_world.database.entities.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert
    void insertNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);

    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Query("SELECT * FROM notification WHERE id_user=:id_user")
    List<Notification> findNotificationsForUser(final int id_user);
}
