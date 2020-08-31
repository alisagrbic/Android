package com.example.dance_world.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dance_world.database.entities.Comment;

import java.util.List;

@Dao
public interface CommentDAO {
    @Insert
    void insertComment(Comment comment);

    @Delete
    void deleteComment(Comment comment);

    @Query("SELECT * FROM comment")
    List<Comment> getAll();

    @Query("SELECT * FROM comment WHERE id_user=:id_user")
    List<Comment> findCommentsForUser(final long id_user);

    @Query("SELECT * FROM comment WHERE id_festival=:id_festival")
    List<Comment> findCommentsForFestival(final long id_festival);
}
