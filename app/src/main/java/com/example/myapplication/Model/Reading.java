package com.example.myapplication.Model;

public class Reading {
    public int id;
    public int unitId;
    public String title;
    public String content;

    public Reading(int id, int unitId, String title, String content) {
        this.id = id;
        this.unitId = unitId;
        this.title = title;
        this.content = content;
    }
}
