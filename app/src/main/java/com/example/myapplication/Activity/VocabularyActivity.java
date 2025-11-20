package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class VocabularyActivity extends AppCompatActivity {

    Button btnFlashcard, btnMatching, btnWriting;
    int unitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_practice);

        // Lấy unit_id từ màn hình trước
        unitId = getIntent().getIntExtra("unit_id", 1);

        // ------------------ TOOLBAR ------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Practice Vocabulary – Unit " + unitId);

        // ------------------ ÁNH XẠ VIEW ------------------
        btnFlashcard = findViewById(R.id.btnFlashcard);
        btnMatching = findViewById(R.id.btnMatching);
        btnWriting = findViewById(R.id.btnWriting);

        // ------------------ FLASHCARD ------------------
        btnFlashcard.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlashcardActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });

        // ------------------ MATCHING GAME ------------------
        btnMatching.setOnClickListener(v -> {
            Intent intent = new Intent(this, WordMatchingActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });

        // ------------------ WRITING TEST ------------------
        btnWriting.setOnClickListener(v -> {
            Intent intent = new Intent(this, WritingTestActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });

    }
}
