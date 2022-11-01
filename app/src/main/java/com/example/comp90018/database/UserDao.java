package com.example.comp90018.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("UPDATE user SET emotion = :sEmotion, location = :sLocation, activity =:sActivity WHERE ID = :sID")
    void update(int sID, String sEmotion, String sLocation, String sActivity);

    @Query("SELECT * FROM user WHERE day = :sDay AND month = :sMonth AND year = :sYear")
    List<User> get(String sDay, String sMonth, String sYear);
}
