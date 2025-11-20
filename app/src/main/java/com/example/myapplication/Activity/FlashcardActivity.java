package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

    CardView cardFront, cardBack;
    TextView tvFront, tvBack;

    AnimatorSet flipOut, flipIn;

    List<String> wordList = new ArrayList<>();
    List<String> meaningList = new ArrayList<>();
    List<String> exampleList = new ArrayList<>();

    int currentIndex = 0;
    boolean showingFront = true;

    int unitId;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // ---------------- TOOLBAR ----------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        unitId = getIntent().getIntExtra("unit_id", 1);
        getSupportActionBar().setTitle("Flashcards – Unit " + unitId);

        // ---------------- ÁNH XẠ VIEW ----------------
        cardFront = findViewById(R.id.cardFront);
        cardBack  = findViewById(R.id.cardBack);
        tvFront   = findViewById(R.id.tvFront);
        tvBack    = findViewById(R.id.tvBack);

        // ---------------- LOAD ANIMATION ----------------
        flipOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_out);
        flipIn  = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_in);

        // ---------------- LOAD DATA ----------------
        db = new DBHelper(this);
        loadVocabulary(unitId);

        if (wordList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu từ vựng!", Toast.LENGTH_LONG).show();
            return;
        }

        showCard(currentIndex);

        // ---------------- SỰ KIỆN LẬT THẺ ----------------
        cardFront.setOnClickListener(v -> flipCard());
        cardBack.setOnClickListener(v -> flipCard());

        // ---------------- NEXT CARD ----------------
        findViewById(R.id.btnNextCard).setOnClickListener(v -> nextCard());
    }


    // ============================================================
    // LOAD VOCABULARY DATA
    // ============================================================
    private void loadVocabulary(int unitId) {
        // lấy dữ liệu dạng đầy đủ (word – meaning – example)
        List<String> fullList = db.getVocabularyList(unitId);

        for (String item : fullList) {
            // dạng: "word – meaning\nVD: example"
            String[] parts = item.split("–");

            if (parts.length >= 2) {
                String word = parts[0].trim();
                String meaningBlock = parts[1];

                String meaning = meaningBlock.split("\n")[0].trim();

                wordList.add(word);
                meaningList.add(meaning);
            }
        }
    }

    // ============================================================
    // HIỂN THỊ CARD
    // ============================================================
    private void showCard(int index) {
        tvFront.setText(wordList.get(index));
        tvBack.setText(meaningList.get(index));

        // đảm bảo mặt trước hiển thị khi đổi từ
        showingFront = true;
        cardFront.setAlpha(1);
        cardBack.setAlpha(0);
    }

    // ============================================================
    // LẬT THẺ
    // ============================================================
    private void flipCard() {
        if (showingFront) {
            flipOut.setTarget(cardFront);
            flipIn.setTarget(cardBack);
        } else {
            flipOut.setTarget(cardBack);
            flipIn.setTarget(cardFront);
        }

        flipOut.start();
        flipIn.start();

        showingFront = !showingFront;
    }

    // ============================================================
    // NEXT CARD
    // ============================================================
    private void nextCard() {
        currentIndex++;

        if (currentIndex >= wordList.size()) {
            currentIndex = 0;
            Toast.makeText(this, "Đã quay về thẻ đầu tiên!", Toast.LENGTH_SHORT).show();
        }

        showCard(currentIndex);
    }
}
