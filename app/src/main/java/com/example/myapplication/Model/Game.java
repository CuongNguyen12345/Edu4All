package com.example.myapplication.Model;

public class Game {
    private String title;
    private int iconResId; // Using int for drawable resource ID

    public Game(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}
