package com.example.myapplication.Model;

public class Chapter {
    private String title;
    private String lessonCount;
    private int progress; // Added for the progress bar

    public Chapter(String title, String lessonCount, int progress) {
        this.title = title;
        this.lessonCount = lessonCount;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public String getLessonCount() {
        return lessonCount;
    }

    public int getProgress() {
        return progress;
    }
}
