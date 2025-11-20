package com.example.myapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class Question2 {

    public int id;
    public int unitId;
    public String question2;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correct;

    private List<String> options;
    private int correctOptionIndex;

    public Question2(int id, int unitId, String question,
                    String optionA, String optionB, String optionC, String optionD,
                    String correct) {

        this.id = id;
        this.unitId = unitId;
        this.question2 = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correct = correct;

        options = new ArrayList<>();
        options.add(optionA);
        options.add(optionB);
        options.add(optionC);
        options.add(optionD);

        switch (correct) {
            case "A": correctOptionIndex = 0; break;
            case "B": correctOptionIndex = 1; break;
            case "C": correctOptionIndex = 2; break;
            case "D": correctOptionIndex = 3; break;
        }
    }

    public String getQuestionText2() { return question2; }
    public List<String> getOptions2() { return options; }
    public int getCorrectOptionIndex2() { return correctOptionIndex; }
}
