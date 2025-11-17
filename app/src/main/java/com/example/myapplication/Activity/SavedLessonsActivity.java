package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ChapterAdapter;
import com.example.myapplication.Model.Chapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SavedLessonsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvSavedLessons;
    private TextView tvEmptyState;
    private List<Chapter> savedLessonsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_lessons);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rvSavedLessons = findViewById(R.id.rvSavedLessons);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        // In a real app, you would load saved lessons from a database
        // For now, we just show the empty state.
        if (savedLessonsList.isEmpty()) {
            rvSavedLessons.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            // Setup RecyclerView with an adapter
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
