package com.example.myapplication.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "users")
@Getter
@Setter
public class UserEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String email;
    private String password;

    // Added for XP and Level system
    private int level;
    private int xp;

    // This is the constructor Room will use
    public UserEntity() {
    }

    // Tell Room to ignore this constructor
    @Ignore
    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = 1; // Start at level 1
        this.xp = 0;    // Start with 0 XP
    }
}
