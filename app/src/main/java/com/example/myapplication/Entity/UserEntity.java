package com.example.myapplication.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String address;

    public UserEntity() {

    }

    public UserEntity(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
