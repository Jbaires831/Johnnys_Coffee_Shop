package com.example.johnnyscoffeeshop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.johnnyscoffeeshop.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User...users);

    //get users info
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    List<User> getUserbyId(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :userName")
    User getUserbyUserName(String userName);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();
}
