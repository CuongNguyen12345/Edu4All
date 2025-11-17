package com.example.myapplication.Model;

public class Word {
    private String front;
    private String back;

    public Word(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
}
