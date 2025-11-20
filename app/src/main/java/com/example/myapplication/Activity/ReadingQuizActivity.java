package com.example.myapplication.Activity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Model.Question2;
import com.example.myapplication.Model.Reading;
import com.example.myapplication.R;

import java.util.List;

public class ReadingQuizActivity extends AppCompatActivity {

    TextView txtReadingContent, tvQ1, tvQ2, tvQ3, tvQ4, tvQ5;
    RadioGroup rgQ1, rgQ2, rgQ3, rgQ4, rgQ5;
    Button btnSubmit;

    DBHelper db;
    List<Question2> list;
    int unitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_quiz);

        unitId = getIntent().getIntExtra("unit_id", 1);
        db = new DBHelper(this);

        initViews();

        Reading reading = db.getReading(unitId);
        if (reading != null) {
            txtReadingContent.setText(reading.content);
        }

        list = db.getQuestions(unitId);
        if (list.size() < 5) {
            Toast.makeText(this, "Không đủ câu hỏi!", Toast.LENGTH_LONG).show();
            return;
        }

        loadQuestions();

        btnSubmit.setOnClickListener(v -> checkScore());
    }

    private void initViews() {
        txtReadingContent = findViewById(R.id.txtReadingContent);

        tvQ1 = findViewById(R.id.tvQ1);
        tvQ2 = findViewById(R.id.tvQ2);
        tvQ3 = findViewById(R.id.tvQ3);
        tvQ4 = findViewById(R.id.tvQ4);
        tvQ5 = findViewById(R.id.tvQ5);

        rgQ1 = findViewById(R.id.rgQ1);
        rgQ2 = findViewById(R.id.rgQ2);
        rgQ3 = findViewById(R.id.rgQ3);
        rgQ4 = findViewById(R.id.rgQ4);
        rgQ5 = findViewById(R.id.rgQ5);

        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void loadQuestions() {
        bindQuestion(tvQ1, rgQ1, list.get(0));
        bindQuestion(tvQ2, rgQ2, list.get(1));
        bindQuestion(tvQ3, rgQ3, list.get(2));
        bindQuestion(tvQ4, rgQ4, list.get(3));
        bindQuestion(tvQ5, rgQ5, list.get(4));
    }

    private void bindQuestion(TextView tv, RadioGroup rg, Question2 q) {
        tv.setText(q.getQuestionText2());

        ((RadioButton) rg.getChildAt(0)).setText("A. " + q.optionA);
        ((RadioButton) rg.getChildAt(1)).setText("B. " + q.optionB);
        ((RadioButton) rg.getChildAt(2)).setText("C. " + q.optionC);
        ((RadioButton) rg.getChildAt(3)).setText("D. " + q.optionD);
    }

    private void checkScore() {
        int score = 0;

        if (isCorrect(rgQ1, list.get(0))) score++;
        if (isCorrect(rgQ2, list.get(1))) score++;
        if (isCorrect(rgQ3, list.get(2))) score++;
        if (isCorrect(rgQ4, list.get(3))) score++;
        if (isCorrect(rgQ5, list.get(4))) score++;

        Toast.makeText(this, "Bạn đúng " + score + "/5 câu", Toast.LENGTH_LONG).show();
    }

    private boolean isCorrect(RadioGroup rg, Question2 q) {
        int checkedId = rg.getCheckedRadioButtonId();
        if (checkedId == -1) return false;

        RadioButton rb = findViewById(checkedId);

        String chosen = rb.getText().toString().substring(0, 1).toUpperCase();

        return chosen.equals(q.correct);
    }
}
