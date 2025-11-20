package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.myapplication.R;

public class SubjectSelectionActivity extends AppCompatActivity {

    private CardView cardMath, cardPhysics, cardEnglish;
    private TextView tvMajorGroup;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);

        // --- Logic is now re-enabled ---
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        cardMath = findViewById(R.id.cardMath);
        cardPhysics = findViewById(R.id.cardPhysics);
        cardEnglish = findViewById(R.id.cardEnglish);
        tvMajorGroup = findViewById(R.id.tvMajorGroup);

        String majorGroup = getIntent().getStringExtra("MAJOR_GROUP");
        if (majorGroup != null) {
            tvMajorGroup.setText("Khối " + majorGroup);
            updateSubjectVisibility(majorGroup);
        }

        // --- Click listeners for navigation ---

        // Navigate to the generic SubjectDetailActivity for Math
        cardMath.setOnClickListener(v -> {
            Intent intent = new Intent(SubjectSelectionActivity.this, SubjectDetailActivity.class);
            intent.putExtra("SUBJECT_NAME", "Toán");
            startActivity(intent);
        });

        // Navigate to the generic SubjectDetailActivity for Physics
        cardPhysics.setOnClickListener(v -> {
            Intent intent = new Intent(SubjectSelectionActivity.this, SubjectDetailActivity.class);
            intent.putExtra("SUBJECT_NAME", "Lý");
            startActivity(intent);
        });

        // Navigate to the rich EnglishSubjectActivity for English
        cardEnglish.setOnClickListener(v -> {
            Intent intent = new Intent(SubjectSelectionActivity.this, SubjectDetailActivity.class);
            intent.putExtra("SUBJECT_NAME", "Anh");
            intent.putExtra("unit_id", 1);
            startActivity(intent);
        });
    }

    private void updateSubjectVisibility(String majorGroup) {
        // You can expand this logic for other major groups
        if ("A".equals(majorGroup)) {
            cardMath.setVisibility(View.VISIBLE);
            cardPhysics.setVisibility(View.VISIBLE);
            cardEnglish.setVisibility(View.VISIBLE);
        } else {
            // For other groups, you might hide some cards
            // For example, for group C, you'd hide Math and Physics
            cardMath.setVisibility(View.GONE);
            cardPhysics.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
