package com.example.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM users WHERE username =:username")
    UserEntity getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email =:email")
    UserEntity getUserByEmailAddress(String email);

    @Insert
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);
}
