package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dance_world.database.entities.Ticket;

import java.util.List;

@Dao
public interface TicketDAO {
    @Insert
    void insertTicket(Ticket ticket);

    @Delete
    void deleteTicket(Ticket ticket);

    @Query("SELECT * FROM ticket")
    List<Ticket> getAll();

    @Query("SELECT * FROM ticket WHERE id_user=:id_user")
    List<Ticket> findTicketsForUser(final int id_user);

    @Query("SELECT * FROM ticket WHERE id_festival=:id_festival")
    List<Ticket> findFestivalsForUser(final int id_festival);
}
