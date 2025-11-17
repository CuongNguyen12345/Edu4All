package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ChapterAdapter;
import com.example.myapplication.Adapter.GameAdapter;
import com.example.myapplication.Model.Chapter;
import com.example.myapplication.Model.Game;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EnglishSubjectActivity extends AppCompatActivity {

    private RecyclerView rvGames, rvGrammar, rvReading, rvListening, rvKnowledgeCheck;
    private GameAdapter gameAdapter;
    private ChapterAdapter grammarAdapter, readingAdapter, listeningAdapter, knowledgeCheckAdapter;
    private List<Game> gameList;
    private List<Chapter> grammarList, readingList, listeningList, knowledgeCheckList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_subject);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        configureRecyclerViews();
        setupAllSections();
    }

    private void configureRecyclerViews() {
        rvGames = findViewById(R.id.rvGames);
        rvGames.setLayoutManager(new GridLayoutManager(this, 3));
        rvGames.setHasFixedSize(true);

        rvGrammar = findViewById(R.id.rvGrammar);
        rvGrammar.setLayoutManager(new LinearLayoutManager(this));
        rvGrammar.setNestedScrollingEnabled(false);

        rvReading = findViewById(R.id.rvReading);
        rvReading.setLayoutManager(new LinearLayoutManager(this));
        rvReading.setNestedScrollingEnabled(false);

        rvListening = findViewById(R.id.rvListening);
        rvListening.setLayoutManager(new LinearLayoutManager(this));
        rvListening.setNestedScrollingEnabled(false);

        rvKnowledgeCheck = findViewById(R.id.rvKnowledgeCheck);
        rvKnowledgeCheck.setLayoutManager(new LinearLayoutManager(this));
        rvKnowledgeCheck.setNestedScrollingEnabled(false);
    }

    private void setupAllSections() {
        setupGameSection();
        setupGrammarSection();
        setupReadingSection();
        setupListeningSection();
        setupKnowledgeCheckSection();
    }

    private void setupGameSection() {
        gameList = new ArrayList<>();
        gameList.add(new Game(getString(R.string.game_flashcards), 0));
        gameList.add(new Game(getString(R.string.game_word_matching), 0));
        gameList.add(new Game(getString(R.string.game_grammar_quiz), 0));

        gameAdapter = new GameAdapter(gameList);
        gameAdapter.setOnItemClickListener(position -> {
            String gameTitle = gameList.get(position).getTitle();
            if (gameTitle.equals(getString(R.string.game_flashcards))) {
                startActivity(new Intent(EnglishSubjectActivity.this, FlashcardActivity.class));
            } else if (gameTitle.equals(getString(R.string.game_word_matching))) {
                startActivity(new Intent(EnglishSubjectActivity.this, WordMatchingActivity.class));
            } else if (gameTitle.equals(getString(R.string.game_grammar_quiz))) {
                startActivity(new Intent(EnglishSubjectActivity.this, GrammarQuizActivity.class));
            }
        });
        rvGames.setAdapter(gameAdapter);
    }

    private void setupGrammarSection() {
        grammarList = new ArrayList<>();
        grammarList.add(new Chapter("The 12 English Tenses", "12 bài học", 75));
        // CORRECTED: Added null for the listener parameter
        grammarAdapter = new ChapterAdapter(grammarList, null);
        rvGrammar.setAdapter(grammarAdapter);
    }

    private void setupReadingSection() {
        readingList = new ArrayList<>();
        readingList.add(new Chapter("Reading for Main Ideas", "5 bài đọc", 50));
        // CORRECTED: Added null for the listener parameter
        readingAdapter = new ChapterAdapter(readingList, null);
        rvReading.setAdapter(readingAdapter);
    }

    private void setupListeningSection() {
        listeningList = new ArrayList<>();
        listeningList.add(new Chapter("Listening for Details", "6 bài nghe", 20));
        // CORRECTED: Added null for the listener parameter
        listeningAdapter = new ChapterAdapter(listeningList, null);
        rvListening.setAdapter(listeningAdapter);
    }

    private void setupKnowledgeCheckSection() {
        knowledgeCheckList = new ArrayList<>();
        knowledgeCheckList.add(new Chapter("Kiểm tra giữa kỳ - Ngữ pháp", "45 phút", 0));
        // CORRECTED: Added null for the listener parameter
        knowledgeCheckAdapter = new ChapterAdapter(knowledgeCheckList, null);
        rvKnowledgeCheck.setAdapter(knowledgeCheckAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
