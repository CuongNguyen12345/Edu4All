package com.example.myapplication.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(tableName = "users")
@Getter
@Setter

public class UserEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String password;

    public UserEntity() {

    }

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
