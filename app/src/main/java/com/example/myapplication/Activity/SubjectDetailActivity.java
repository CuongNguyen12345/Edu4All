package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ChapterAdapter;
import com.example.myapplication.Model.Chapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvChapters;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;
    private String currentSubject;
    private String currentTab = "Theory"; // Keep track of the current tab

    private TextView tabTheory, tabDocument, tabExam, tabQuickGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvChapters = findViewById(R.id.rvChapters);
        rvChapters.setLayoutManager(new LinearLayoutManager(this));

        tabTheory = findViewById(R.id.tabTheory);
        tabDocument = findViewById(R.id.tabDocument);
        tabExam = findViewById(R.id.tabExam);
        tabQuickGame = findViewById(R.id.tabQuickGame);

        currentSubject = getIntent().getStringExtra("SUBJECT_NAME");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (currentSubject != null) {
                getSupportActionBar().setTitle("Môn " + currentSubject);
            }
        }

        setupTabListeners();

        loadChapterList(currentTab);
        updateTabSelection(currentTab);
    }

    private void setupTabListeners() {
        tabTheory.setOnClickListener(v -> {
            currentTab = "Theory";
            loadChapterList(currentTab);
            updateTabSelection(currentTab);
        });
        tabDocument.setOnClickListener(v -> {
            currentTab = "Document";
            loadChapterList(currentTab);
            updateTabSelection(currentTab);
        });
        tabExam.setOnClickListener(v -> {
            currentTab = "Exam";
            loadChapterList(currentTab);
            updateTabSelection(currentTab);
        });
        tabQuickGame.setOnClickListener(v -> {
            currentTab = "QuickGame";
            loadChapterList(currentTab);
            updateTabSelection(currentTab);
        });
    }

    private void loadChapterList(String tab) {
        chapterList = new ArrayList<>();
        if ("Anh".equals(currentSubject)) {
            switch (tab) {
                case "Theory":
                    chapterList.add(new Chapter("Unit 1: Family Life", "15 bài học", 50));
                    chapterList.add(new Chapter("Unit 2: Your Body and You", "12 bài học", 20));
                    break;
                case "QuickGame":
                    chapterList.add(new Chapter("Flashcards", "Học từ vựng qua thẻ", 0));
                    chapterList.add(new Chapter("Word Matching", "Nối từ với nghĩa", 0));
                    chapterList.add(new Chapter("Grammar Quiz", "Trắc nghiệm ngữ pháp", 0));
                    break;
                // Add other cases for Document, Exam...
            }
        } else {
             // Logic for Math, Physics... (unchanged)
        }

        chapterAdapter = new ChapterAdapter(chapterList, chapter -> {
            if ("QuickGame".equals(tab)) {
                if ("Flashcards".equals(chapter.getTitle())) {
                    startActivity(new Intent(this, FlashcardActivity.class));
                } else if ("Word Matching".equals(chapter.getTitle())) {
                    startActivity(new Intent(this, WordMatchingActivity.class));
                } else if ("Grammar Quiz".equals(chapter.getTitle())) {
                    startActivity(new Intent(this, GrammarQuizActivity.class));
                }
            } else if ("Theory".equals(tab) && "Anh".equals(currentSubject)) {
                Intent intent = new Intent(this, UnitDetailActivity.class);
                intent.putExtra("UNIT_TITLE", chapter.getTitle());
                startActivity(intent);
            }
        });
        rvChapters.setAdapter(chapterAdapter);
    }

    private void updateTabSelection(String selectedTab) {
        // ... (unchanged)
    }

    private void updateTabTextStyle(TextView tab, boolean isSelected) {
        // ... (unchanged)
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
