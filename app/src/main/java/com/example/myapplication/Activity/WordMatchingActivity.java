package com.example.myapplication.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.Model.Word;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordMatchingActivity extends AppCompatActivity {

    private LinearLayout englishColumn, vietnameseColumn;
    private Button btnNextRound;

    private List<Word> currentRoundWords;
    private Map<TextView, Word> textViewToWordMap = new HashMap<>();

    private TextView selectedEnglishView = null;
    private TextView selectedVietnameseView = null;

    private int correctPairs = 0;

    // XP System
    private SharedPrefManager sharedPrefManager;
    private static final int XP_FOR_ROUND_COMPLETION = 30;
    private static final int XP_TO_LEVEL_UP = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_matching);

        sharedPrefManager = new SharedPrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        englishColumn = findViewById(R.id.englishColumn);
        vietnameseColumn = findViewById(R.id.vietnameseColumn);
        btnNextRound = findViewById(R.id.btnNextRound);

        startNewRound();
        btnNextRound.setOnClickListener(v -> startNewRound());
    }

    private void startNewRound() {
        // ... (Reset state logic is unchanged)
        correctPairs = 0;
        englishColumn.removeAllViews();
        vietnameseColumn.removeAllViews();
        textViewToWordMap.clear();
        selectedEnglishView = null;
        selectedVietnameseView = null;
        btnNextRound.setVisibility(View.INVISIBLE);

        prepareWords();
        displayWords();
    }

    private void prepareWords() {
        // ... (Unchanged)
    }

    private void displayWords() {
        // ... (Unchanged)
    }

    private TextView createWordTextView(String text, boolean isEnglish) {
        // ... (Unchanged)
        return new TextView(this); // Placeholder
    }

    private void onWordSelected(TextView view, boolean isEnglish) {
        // ... (Unchanged)
    }

    private void checkMatch() {
        Word englishWord = textViewToWordMap.get(selectedEnglishView);
        Word vietnameseWord = textViewToWordMap.get(selectedVietnameseView);

        final TextView tempEngView = selectedEnglishView;
        final TextView tempVieView = selectedVietnameseView;

        if (englishWord == vietnameseWord) {
            correctPairs++;
            setButtonState(tempEngView, true);
            setButtonState(tempVieView, true);
            tempEngView.setOnClickListener(null);
            tempVieView.setOnClickListener(null);

            if (correctPairs == currentRoundWords.size()) {
                Toast.makeText(this, "Hoàn thành! +" + XP_FOR_ROUND_COMPLETION + " XP", Toast.LENGTH_SHORT).show();
                awardXp(XP_FOR_ROUND_COMPLETION);
                btnNextRound.setVisibility(View.VISIBLE);
            }
        } else {
            // ... (Incorrect match logic is unchanged)
        }

        selectedEnglishView = null;
        selectedVietnameseView = null;
    }

    private void awardXp(int amount) {
        int currentXp = sharedPrefManager.getXp();
        int currentLevel = sharedPrefManager.getLevel();

        currentXp += amount;
        if (currentXp >= XP_TO_LEVEL_UP) {
            currentLevel++;
            currentXp -= XP_TO_LEVEL_UP;
        }
        sharedPrefManager.saveUserStats(currentXp, currentLevel);
    }

    private void setButtonState(TextView view, boolean isCorrect) {
        // ... (Unchanged)
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
