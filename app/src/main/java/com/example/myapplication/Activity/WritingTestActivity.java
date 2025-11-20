package com.example.myapplication.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WritingTestActivity extends AppCompatActivity {

    TextView tvMeaning, tvCorrectAnswer;
    EditText edtAnswer;

    DBHelper db;
    int unitId;

    List<String> wordList = new ArrayList<>();
    List<String> meaningList = new ArrayList<>();

    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_test);

        unitId = getIntent().getIntExtra("unit_id", 1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Writing – Unit " + unitId);

        // View mapping
        tvMeaning = findViewById(R.id.tvMeaning);
        edtAnswer = findViewById(R.id.edtAnswer);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);

        db = new DBHelper(this);

        loadData();
        showWord();

        findViewById(R.id.btnCheck).setOnClickListener(v -> checkAnswer());
        findViewById(R.id.btnNext).setOnClickListener(v -> nextWord());
    }

    // ============================================================
    // LOAD DATA
    // ============================================================
    private void loadData() {
        List<String> fullList = db.getVocabularyList(unitId);

        for (String item : fullList) {
            // "word – meaning\nVD: ..."
            String[] parts = item.split("–");

            if (parts.length >= 2) {
                String word = parts[0].trim();
                String meaning = parts[1].trim().split("\n")[0];

                wordList.add(word);
                meaningList.add(meaning);
            }
        }
    }

    // ============================================================
    // SHOW CURRENT WORD (meaning only)
    // ============================================================
    private void showWord() {
        tvMeaning.setText(meaningList.get(currentIndex));
        edtAnswer.setText("");

        tvCorrectAnswer.setVisibility(TextView.GONE);
    }

    // ============================================================
    // CHECK ANSWER
    // ============================================================
    private void checkAnswer() {
        String userInput = edtAnswer.getText().toString().trim().toLowerCase();
        String correctWord = wordList.get(currentIndex).trim().toLowerCase();

        if (userInput.equals(correctWord)) {

            tvCorrectAnswer.setText("✓ ĐÚNG!");
            tvCorrectAnswer.setTextColor(getColor(android.R.color.holo_green_dark));
            tvCorrectAnswer.setVisibility(TextView.VISIBLE);

        } else {

            tvCorrectAnswer.setText("✗ Sai!  Từ đúng: " + wordList.get(currentIndex));
            tvCorrectAnswer.setTextColor(getColor(android.R.color.holo_red_dark));
            tvCorrectAnswer.setVisibility(TextView.VISIBLE);
        }
    }

    // ============================================================
    // NEXT WORD
    // ============================================================
    private void nextWord() {
        currentIndex++;

        if (currentIndex >= wordList.size()) {
            currentIndex = 0;
        }

        showWord();
    }
}
