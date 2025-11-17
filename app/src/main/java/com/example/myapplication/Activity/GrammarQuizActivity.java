package com.example.myapplication.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog; // CORRECTED: Added this import
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.Model.Question;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrammarQuizActivity extends AppCompatActivity {

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView tvQuestionNumber, tvQuestionText;
    private RadioGroup radioGroupAnswers;
    private RadioButton[] radioButtons = new RadioButton[3];
    private Button btnCheckAnswer;

    private SharedPrefManager sharedPrefManager;
    private static final int XP_PER_CORRECT_ANSWER = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_quiz);

        sharedPrefManager = new SharedPrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initializeViews();
        prepareQuestions();
        displayCurrentQuestion();

        btnCheckAnswer.setOnClickListener(v -> checkAnswer());
    }

    private void initializeViews() {
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestionText = findViewById(R.id.tvQuestionText);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        radioButtons[0] = findViewById(R.id.radioAnswer1);
        radioButtons[1] = findViewById(R.id.radioAnswer2);
        radioButtons[2] = findViewById(R.id.radioAnswer3);
        btnCheckAnswer = findViewById(R.id.btnCheckAnswer);
    }

    private void prepareQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("I ___ a student.", Arrays.asList("is", "am", "are"), 1));
        questionList.add(new Question("She ___ to the store yesterday.", Arrays.asList("go", "goes", "went"), 2));
        questionList.add(new Question("They ___ watching a movie now.", Arrays.asList("are", "is", "be"), 0));
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            tvQuestionNumber.setText("Câu " + (currentQuestionIndex + 1) + "/" + questionList.size());
            tvQuestionText.setText(currentQuestion.getQuestionText());

            for (int i = 0; i < radioButtons.length; i++) {
                radioButtons[i].setText(currentQuestion.getOptions().get(i));
                radioButtons[i].setBackgroundResource(R.drawable.selector_word_button);
                radioButtons[i].setEnabled(true);
            }
            radioGroupAnswers.clearCheck();
            btnCheckAnswer.setText(R.string.check_answer);

        } else {
            showFinalScore();
        }
    }

    private void checkAnswer() {
        int selectedId = radioGroupAnswers.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        int selectedOptionIndex = radioGroupAnswers.indexOfChild(selectedRadioButton);
        Question currentQuestion = questionList.get(currentQuestionIndex);

        for (RadioButton rb : radioButtons) {
            rb.setEnabled(false);
        }

        if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
            selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.subject_green));
            score++;
            awardXp(XP_PER_CORRECT_ANSWER);
            Toast.makeText(this, "Chính xác! +" + XP_PER_CORRECT_ANSWER + " XP", Toast.LENGTH_SHORT).show();
        } else {
            selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.subject_red));
            radioButtons[currentQuestion.getCorrectOptionIndex()].setBackgroundColor(ContextCompat.getColor(this, R.color.subject_green));
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            currentQuestionIndex++;
            displayCurrentQuestion();
        }, 1500);
    }

    private void showFinalScore() {
        new AlertDialog.Builder(this)
            .setTitle("Hoàn thành!")
            .setMessage("Bạn đã trả lời đúng " + score + "/" + questionList.size() + " câu.")
            .setPositiveButton("Chơi lại", (dialog, which) -> {
                currentQuestionIndex = 0;
                score = 0;
                displayCurrentQuestion();
            })
            .setNegativeButton("Thoát", (dialog, which) -> finish())
            .setCancelable(false)
            .show();
    }

    private void awardXp(int amount) {
        int currentXp = sharedPrefManager.getXp();
        int currentLevel = sharedPrefManager.getLevel();
        currentXp += amount;
        if (currentXp >= 100) { 
            currentLevel++;
            currentXp -= 100;
        }
        sharedPrefManager.saveUserStats(currentXp, currentLevel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
