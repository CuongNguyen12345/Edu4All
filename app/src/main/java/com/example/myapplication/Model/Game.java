package com.example.myapplication.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private String title;
    private int iconResId; // Using int for drawable resource ID

    public Game(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

}
