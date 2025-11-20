package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Database.DBHelper;
import com.example.myapplication.Model.Reading;
import com.example.myapplication.R;

import java.util.List;

public class EnglishSubjectActivity1 extends AppCompatActivity {

    private static final String TAG = "EnglishSubject";

    TextView tvReading, tvVocabulary, tvListen;
    Button btnPracticeReading, btnPracticeVocabulary, btnPlayAudio;
    ImageView imgBanner;

    DBHelper db;
    int unitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_subject1);

        Log.d(TAG, "EnglishSubjectActivity1 CREATED");

        // ========================= 1) UNIT ID =========================
        unitId = getIntent().getIntExtra("unit_id", 1);
        Log.d(TAG, "unitId = " + unitId);

        // ========================= 2) TOOLBAR =========================
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String unitName = getUnitTitle(unitId);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Unit " + unitId + " – " + unitName);
        }

        // ========================= 3) ÁNH XẠ VIEW =========================
        tvReading = findViewById(R.id.tvReading);
        tvVocabulary = findViewById(R.id.tvVocabulary);
        tvListen = findViewById(R.id.tvListen);

        btnPracticeReading = findViewById(R.id.btnPracticeReading);
        btnPracticeVocabulary = findViewById(R.id.btnPracticeVocabulary);
        btnPlayAudio = findViewById(R.id.btnPlayAudio);

        imgBanner = findViewById(R.id.imgUnitBanner);

        // ========================= 4) SET BANNER IMAGE =========================
        setBannerImage(unitId);

        // ========================= 5) DATABASE =========================
        try {
            db = new DBHelper(this);
        } catch (Exception e) {
            Log.e(TAG, "DB error: " + e.getMessage());
            return;
        }

        loadReading();
        loadVocabulary();
        loadListening();

        // ========================= 6) BUTTON EVENT =========================
        btnPracticeReading.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadingQuizActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });

        btnPracticeVocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(this, VocabularyActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });

        btnPlayAudio.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListeningActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
        });
    }

    // ========================= BANNER IMAGE =========================
    private void setBannerImage(int unitId) {
        switch (unitId) {
            case 1:
                imgBanner.setImageResource(R.drawable.unit_1_banner);
                break;
            case 2:
                imgBanner.setImageResource(R.drawable.unit_2_banner);
                break;
            case 3:
                imgBanner.setImageResource(R.drawable.unit_3_banner);
                break;
            case 4:
                imgBanner.setImageResource(R.drawable.unit_4_banner);
                break;
            case 5:
                imgBanner.setImageResource(R.drawable.unit_4_banner);
                break;
            default:
                imgBanner.setImageResource(R.drawable.unit_1_banner);
        }
    }

    // ========================= LOAD READING =========================
    private void loadReading() {
        try {
            Reading reading = db.getReading(unitId);
            if (reading != null) tvReading.setText(reading.content);
            else tvReading.setText("Không có bài đọc.");
        } catch (Exception e) {
            tvReading.setText("Lỗi tải bài đọc!");
            Log.e(TAG, "Reading error", e);
        }
    }

    // ========================= LOAD VOCABULARY =========================
    private void loadVocabulary() {
        try {
            List<String> list = db.getVocabularyList(unitId);

            if (list.isEmpty()) {
                tvVocabulary.setText("Không có từ vựng.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (String s : list) sb.append("• ").append(s).append("\n\n");

            tvVocabulary.setText(sb.toString());

        } catch (Exception e) {
            tvVocabulary.setText("Lỗi tải từ vựng!");
            Log.e(TAG, "Vocabulary error", e);
        }
    }

    // ========================= LOAD LISTENING SCRIPT =========================
    private void loadListening() {
        try {
            String script = db.getListeningTranscript(unitId);

            if (script == null || script.trim().isEmpty())
                tvListen.setText("Không có script.");
            else
                tvListen.setText(script);

        } catch (Exception e) {
            tvListen.setText("Lỗi tải script!");
            Log.e(TAG, "Listening error", e);
        }
    }

    // ========================= UNIT NAME MAP =========================
    private String getUnitTitle(int unitId) {
        switch (unitId) {
            case 1:
                return "Life Stories";
            case 2:
                return "Urbanisation";
            case 3:
                return "The Green Movement";
            case 4:
                return "Education System";
            case 5:
                return "Cultural Identity";
            default:
                return "Unit " + unitId;
        }
    }
}
