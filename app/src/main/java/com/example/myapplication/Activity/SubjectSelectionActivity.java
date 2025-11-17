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

        // CORRECTED: All subjects now open the same detail activity
        cardMath.setOnClickListener(v -> openSubjectDetail("Toán"));
        cardPhysics.setOnClickListener(v -> openSubjectDetail("Lý"));
        cardEnglish.setOnClickListener(v -> openSubjectDetail("Anh"));
    }

    private void openSubjectDetail(String subjectName) {
        Intent intent = new Intent(SubjectSelectionActivity.this, SubjectDetailActivity.class);
        intent.putExtra("SUBJECT_NAME", subjectName);
        startActivity(intent);
    }

    private void updateSubjectVisibility(String majorGroup) {
        // You can expand this logic for other major groups
        if ("A".equals(majorGroup)) {
            cardMath.setVisibility(View.VISIBLE);
            cardPhysics.setVisibility(View.VISIBLE);
            cardEnglish.setVisibility(View.VISIBLE);
        } else {
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
