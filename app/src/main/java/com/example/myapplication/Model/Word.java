package com.example.myapplication.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Word {
    private String front;
    private String back;

    public Word(String front, String back) {
        this.front = front;
        this.back = back;
    }

}
