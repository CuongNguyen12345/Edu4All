package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Entity.UserEntity;
import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    private UserEntity currentUser;
    private SharedPrefManager sharedPrefManager;

    private TextView tvWelcome, tvLevel;
    private ProgressBar xpProgressBar;
    private RadioGroup radioGroupAnswers;
    private Button btnSubmitAnswer;

    private static final int XP_FOR_CORRECT_ANSWER = 50;
    private static final int XP_TO_LEVEL_UP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // CORRECTED: The order of initialization is now fixed.
        // 1. Initialize manager
        sharedPrefManager = new SharedPrefManager(this);
        // 2. Find all views first
        initializeViews();
        // 3. Load data and update UI
        loadUserData();
        // 4. Set listeners for the views
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateXpUI();
    }

    private void initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvLevel = findViewById(R.id.tvLevel);
        xpProgressBar = findViewById(R.id.xpProgressBar);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer);
    }

    private void loadUserData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            currentUser = getIntent().getSerializableExtra("user", UserEntity.class);
        } else {
            currentUser = (UserEntity) getIntent().getSerializableExtra("user");
        }

        if (currentUser != null) {
            tvWelcome.setText("Xin chào, " + currentUser.getUsername() + "!");
            if (sharedPrefManager.getLevel() == 1 && sharedPrefManager.getXp() == 0) {
                sharedPrefManager.saveUserStats(currentUser.getXp(), currentUser.getLevel());
            }
        }
    }

    private void updateXpUI() {
        int currentLevel = sharedPrefManager.getLevel();
        int currentXp = sharedPrefManager.getXp();

        tvLevel.setText(getString(R.string.level_prefix) + " " + currentLevel);
        xpProgressBar.setMax(XP_TO_LEVEL_UP);
        xpProgressBar.setProgress(currentXp);
    }

    private void addXp(int amount) {
        int currentXp = sharedPrefManager.getXp();
        int currentLevel = sharedPrefManager.getLevel();

        currentXp += amount;
        if (currentXp >= XP_TO_LEVEL_UP) {
            currentLevel++;
            currentXp -= XP_TO_LEVEL_UP;
            Toast.makeText(this, "Chúc mừng, bạn đã lên cấp!", Toast.LENGTH_LONG).show();
        }

        sharedPrefManager.saveUserStats(currentXp, currentLevel);
        updateXpUI();
    }

    private void setupListeners() {
        findViewById(R.id.cardKhoiA).setOnClickListener(v -> openSubjectSelection("A"));
        findViewById(R.id.cardKhoiB).setOnClickListener(v -> openSubjectSelection("B"));
        findViewById(R.id.cardKhoiC).setOnClickListener(v -> openSubjectSelection("C"));
        findViewById(R.id.cardKhoiD).setOnClickListener(v -> openSubjectSelection("D"));

        btnSubmitAnswer.setOnClickListener(v -> {
            if (radioGroupAnswers.getCheckedRadioButtonId() == R.id.radioAnswerA) {
                Toast.makeText(this, "Chính xác! +" + XP_FOR_CORRECT_ANSWER + " XP", Toast.LENGTH_SHORT).show();
                addXp(XP_FOR_CORRECT_ANSWER);
            } else {
                Toast.makeText(this, "Sai rồi, thử lại vào ngày mai nhé!", Toast.LENGTH_SHORT).show();
            }
            btnSubmitAnswer.setEnabled(false);
        });
    }

    private void openSubjectSelection(String majorGroup) {
        Intent intent = new Intent(HomeActivity.this, SubjectSelectionActivity.class);
        intent.putExtra("MAJOR_GROUP", majorGroup);
        startActivity(intent);
    }
}
