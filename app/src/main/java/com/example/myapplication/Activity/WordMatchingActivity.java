package com.example.myapplication.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordMatchingActivity extends AppCompatActivity {

    LinearLayout englishColumn, vietnameseColumn;
    TextView selectedEnglish = null;
    TextView selectedVietnamese = null;

    DBHelper db;
    int unitId;

    List<String> words = new ArrayList<>();
    List<String> meanings = new ArrayList<>();

    List<String> fullListRaw;   // lưu toàn bộ để kiểm tra đúng nghĩa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_matching);

        unitId = getIntent().getIntExtra("unit_id", 1);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Matching – Unit " + unitId);

        englishColumn = findViewById(R.id.englishColumn);
        vietnameseColumn = findViewById(R.id.vietnameseColumn);

        db = new DBHelper(this);

        loadData();
        renderColumns();
    }

    // ============================================================
    // LOAD VOCAB FROM CSDL
    // ============================================================
    private void loadData() {
        fullListRaw = db.getVocabularyList(unitId);

        for (String item : fullListRaw) {
            String[] parts = item.split("–");
            if (parts.length >= 2) {
                words.add(parts[0].trim());
                meanings.add(parts[1].trim().split("\n")[0]);
            }
        }

        Collections.shuffle(words);
        Collections.shuffle(meanings);
    }

    // ============================================================
    // RENDER COLUMNS (RELOAD UI)
    // ============================================================
    private void renderColumns() {
        englishColumn.removeAllViews();
        vietnameseColumn.removeAllViews();

        for (String w : words) {
            englishColumn.addView(createWordView(w, true));
        }

        for (String m : meanings) {
            vietnameseColumn.addView(createWordView(m, false));
        }
    }

    // ============================================================
    // CREATE TEXTVIEW (CARD)
    // ============================================================
    private TextView createWordView(String text, boolean isEnglish) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(20f);
        tv.setPadding(24, 24, 24, 24);
        tv.setBackgroundResource(R.drawable.card_background);
        tv.setTextColor(getColor(R.color.primary_blue));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
        params.setMargins(0, 16, 0, 16);
        tv.setLayoutParams(params);

        tv.setOnClickListener(v -> handleClick(tv, isEnglish));

        return tv;
    }

    // ============================================================
    // CLICK EVENT
    // ============================================================
    private void handleClick(TextView tv, boolean isEnglish) {

        tv.setBackgroundResource(R.drawable.card_selected);

        if (isEnglish) {
            if (selectedEnglish != null && selectedEnglish != tv)
                selectedEnglish.setBackgroundResource(R.drawable.card_background);
            selectedEnglish = tv;
        } else {
            if (selectedVietnamese != null && selectedVietnamese != tv)
                selectedVietnamese.setBackgroundResource(R.drawable.card_background);
            selectedVietnamese = tv;
        }

        if (selectedEnglish != null && selectedVietnamese != null) {
            checkMatch();
        }
    }

    // ============================================================
    // CHECK MATCH (REMOVE VIEW)
    // ============================================================
    private void checkMatch() {

        String en = selectedEnglish.getText().toString().trim();
        String vi = selectedVietnamese.getText().toString().trim();

        boolean correct = fullListRaw.stream()
                .anyMatch(item -> item.startsWith(en + " – " + vi));

        if (correct) {
            Toast.makeText(this, "✓ Đúng rồi!", Toast.LENGTH_SHORT).show();

            // Xóa khỏi dữ liệu
            words.remove(en);
            meanings.remove(vi);

            // Xóa TextView khỏi layout
            englishColumn.removeView(selectedEnglish);
            vietnameseColumn.removeView(selectedVietnamese);

        } else {
            Toast.makeText(this, "Sai rồi!", Toast.LENGTH_SHORT).show();
            selectedEnglish.setBackgroundResource(R.drawable.card_background);
            selectedVietnamese.setBackgroundResource(R.drawable.card_background);
        }

        selectedEnglish = null;
        selectedVietnamese = null;
    }
}
