package com.example.dance_world.database;

import android.content.Context;

import com.example.dance_world.database.dao.ArtistDAO;
import com.example.dance_world.database.dao.DjDAO;
import com.example.dance_world.database.dao.FestivalDAO;
import com.example.dance_world.database.dao.NotificationDAO;
import com.example.dance_world.database.dao.TicketDAO;
import com.example.dance_world.database.dao.UserDAO;
import com.example.dance_world.database.entities.Artist;
import com.example.dance_world.database.entities.ArtistFestival;
import com.example.dance_world.database.entities.Dj;
import com.example.dance_world.database.entities.DjFestival;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.Notification;
import com.example.dance_world.database.entities.Ticket;
import com.example.dance_world.database.entities.User;
import com.example.dance_world.database.entities.UserFestival;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        User.class,
        Ticket.class,
        Notification.class,
        Festival.class,
        Dj.class,
        Artist.class,
        UserFestival.class,
        DjFestival.class,
        ArtistFestival.class},
        version = 12,
        exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DATABASE_NAME = "dance_world_db";
    public static  DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseHelper.class,
                    DATABASE_NAME).
                    allowMainThreadQueries().
                    fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract UserDAO UserDao();
    public abstract TicketDAO TicketDao();
    public abstract NotificationDAO NotificationDao();
    public abstract FestivalDAO FestivalDao();
    public abstract DjDAO DjDao();
    public abstract ArtistDAO ArtistDao();
}
