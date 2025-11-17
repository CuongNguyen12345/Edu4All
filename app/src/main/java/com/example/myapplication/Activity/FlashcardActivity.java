package com.example.myapplication.Activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.Model.Word;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

    private List<Word> wordList;
    private int currentWordIndex = 0;
    private static final int XP_FOR_ROUND_COMPLETION = 20;
    private static final int XP_TO_LEVEL_UP = 100;

    private SharedPrefManager sharedPrefManager;
    private AnimatorSet frontAnim, backAnim;
    private boolean isFront = true;

    private CardView cardFront, cardBack;
    private TextView tvFront, tvBack;
    private Button btnNextCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        sharedPrefManager = new SharedPrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
        tvFront = findViewById(R.id.tvFront);
        tvBack = findViewById(R.id.tvBack);
        btnNextCard = findViewById(R.id.btnNextCard);

        loadAnimations();
        prepareWordList();
        displayCurrentWord();

        View.OnClickListener flipClickListener = v -> flipCard();
        cardFront.setOnClickListener(flipClickListener);
        cardBack.setOnClickListener(flipClickListener);

        btnNextCard.setOnClickListener(v -> showNextWord());
    }

    private void loadAnimations() {
        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        // ... (rest of the method is unchanged)
    }

    private void prepareWordList() {
        wordList = new ArrayList<>();
        wordList.add(new Word("Hello", "Xin chào"));
        wordList.add(new Word("World", "Thế giới"));
        wordList.add(new Word("Computer", "Máy tính"));
        wordList.add(new Word("Science", "Khoa học"));
        Collections.shuffle(wordList);
    }

    private void displayCurrentWord() {
        // ... (unchanged)
    }

    private void flipCard() {
        // ... (unchanged)
    }

    private void showNextWord() {
        currentWordIndex++;
        if (currentWordIndex >= wordList.size()) {
            awardXp();
            currentWordIndex = 0; // Loop back
        }

        if (!isFront) {
            flipCard();
        }

        cardFront.postDelayed(this::displayCurrentWord, 300);
    }

    private void awardXp() {
        Toast.makeText(this, "Hoàn thành vòng! +" + XP_FOR_ROUND_COMPLETION + " XP", Toast.LENGTH_SHORT).show();

        int currentXp = sharedPrefManager.getXp();
        int currentLevel = sharedPrefManager.getLevel();

        currentXp += XP_FOR_ROUND_COMPLETION;
        if (currentXp >= XP_TO_LEVEL_UP) {
            currentLevel++;
            currentXp -= XP_TO_LEVEL_UP;
            // You can add a special toast for leveling up here if you want
        }

        sharedPrefManager.saveUserStats(currentXp, currentLevel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
