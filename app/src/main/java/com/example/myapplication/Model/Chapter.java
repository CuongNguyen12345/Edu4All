package com.example.myapplication.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chapter {
    private String title;
    private String lessonCount;
    private int progress; // Added for the progress bar

    public Chapter(String title, String lessonCount, int progress) {
        this.title = title;
        this.lessonCount = lessonCount;
        this.progress = progress;
    }

}
