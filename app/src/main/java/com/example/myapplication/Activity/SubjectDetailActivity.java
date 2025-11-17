package com.example.myapplication.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

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

    private TextView tabTheory, tabDocument, tabExam, tabQuickGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvChapters = findViewById(R.id.rvChapters);
        rvChapters.setLayoutManager(new LinearLayoutManager(this));

        // Find tab views
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

        // Load initial data for the 'Theory' tab
        loadChapterList("Theory");
        updateTabSelection("Theory");
    }

    private void setupTabListeners() {
        tabTheory.setOnClickListener(v -> {
            loadChapterList("Theory");
            updateTabSelection("Theory");
        });
        tabDocument.setOnClickListener(v -> {
            loadChapterList("Document");
            updateTabSelection("Document");
        });
        tabExam.setOnClickListener(v -> {
            loadChapterList("Exam");
            updateTabSelection("Exam");
        });
        // You can add a listener for QuickGame if you build that feature
    }

    private void loadChapterList(String tab) {
        chapterList = new ArrayList<>();
        // This is sample data. You should replace it with your actual data source.

        if ("Toán".equals(currentSubject)) {
            switch (tab) {
                case "Theory":
                    chapterList.add(new Chapter("Chương 1: Đạo hàm và ứng dụng", "10 bài học", 60));
                    chapterList.add(new Chapter("Chương 2: Lũy thừa, Mũ và Logarit", "8 bài học", 25));
                    chapterList.add(new Chapter("Chương 3: Nguyên hàm - Tích phân", "12 bài học", 0));
                    chapterList.add(new Chapter("Chương 4: Số phức", "7 bài học", 0));
                    break;
                case "Document":
                    chapterList.add(new Chapter("Tổng hợp công thức Đạo hàm", "PDF", 100));
                    chapterList.add(new Chapter("50 bài tập Logarit có giải", "PDF", 0));
                    chapterList.add(new Chapter("Casio giải nhanh Tích phân", "Video", 0));
                    break;
                case "Exam":
                    chapterList.add(new Chapter("Đề thi thử THPT 2023 - Chuyên KHTN", "90 phút", 0));
                    chapterList.add(new Chapter("Đề thi thử THPT 2023 - Chuyên Sư Phạm", "90 phút", 0));
                    break;
            }
        } else if ("Lý".equals(currentSubject)) {
             switch (tab) {
                case "Theory":
                    chapterList.add(new Chapter("Chương 1: Dao động cơ", "15 bài học", 40));
                    chapterList.add(new Chapter("Chương 2: Sóng cơ và sóng âm", "11 bài học", 10));
                    chapterList.add(new Chapter("Chương 3: Dòng điện xoay chiều", "18 bài học", 0));
                    break;
                case "Document":
                    chapterList.add(new Chapter("Tóm tắt công thức 3 chương đầu", "PDF", 0));
                    break;
                case "Exam":
                    chapterList.add(new Chapter("Kiểm tra 1 tiết - Dao động cơ", "45 phút", 0));
                    break;
            }
        }

        chapterAdapter = new ChapterAdapter(chapterList);
        rvChapters.setAdapter(chapterAdapter);
    }

    private void updateTabSelection(String selectedTab) {
        updateTabTextStyle(tabTheory, "Theory".equals(selectedTab));
        updateTabTextStyle(tabDocument, "Document".equals(selectedTab));
        updateTabTextStyle(tabExam, "Exam".equals(selectedTab));
        updateTabTextStyle(tabQuickGame, "QuickGame".equals(selectedTab));
    }

    private void updateTabTextStyle(TextView tab, boolean isSelected) {
        if (isSelected) {
            tab.setTypeface(null, Typeface.BOLD);
            tab.setTextColor(ContextCompat.getColor(this, R.color.primary_blue));
        } else {
            tab.setTypeface(null, Typeface.NORMAL);
            tab.setTextColor(ContextCompat.getColor(this, R.color.color_text));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
