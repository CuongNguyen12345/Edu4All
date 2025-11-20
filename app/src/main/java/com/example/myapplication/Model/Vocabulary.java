package com.example.myapplication.Model;

public class Vocabulary {
    public int id;
    public int unitId;
    public String word;
    public String meaning;
    public String example;

    public Vocabulary(int id, int unitId, String word, String meaning, String example) {
        this.id = id;
        this.unitId = unitId;
        this.word = word;
        this.meaning = meaning;
        this.example = example;
    }
}
