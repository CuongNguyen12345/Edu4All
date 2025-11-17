package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private static final int LEVEL_TO_UNLOCK_DARK_MODE = 5;

    private SharedPrefManager sharedPrefManager;
    private SwitchMaterial switchDarkMode;
    private LinearLayout darkModeLayout;
    private TextView tvDarkModeDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPrefManager = new SharedPrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initializeViews();
        setupListeners();
        checkDarkModeUnlock();
    }

    private void initializeViews() {
        switchDarkMode = findViewById(R.id.switchDarkMode);
        darkModeLayout = findViewById(R.id.setting_dark_mode_layout);
        tvDarkModeDescription = findViewById(R.id.tvDarkModeDescription);
        findViewById(R.id.setting_change_password);
        findViewById(R.id.setting_logout);
    }

    private void checkDarkModeUnlock() {
        int userLevel = sharedPrefManager.getLevel();
        boolean isUnlocked = userLevel >= LEVEL_TO_UNLOCK_DARK_MODE;

        switchDarkMode.setEnabled(isUnlocked);

        if (isUnlocked) {
            tvDarkModeDescription.setText(R.string.setting_dark_mode_description);
            // Set the switch to the current theme
            int currentTheme = sharedPrefManager.getTheme();
            switchDarkMode.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            tvDarkModeDescription.setText(getString(R.string.setting_dark_mode_locked));
            switchDarkMode.setChecked(false);
        }
    }

    private void setupListeners() {
        darkModeLayout.setOnClickListener(v -> {
            if (!switchDarkMode.isEnabled()) {
                Toast.makeText(this, "Bạn cần đạt Cấp " + LEVEL_TO_UNLOCK_DARK_MODE + " để mở khóa tính năng này!", Toast.LENGTH_SHORT).show();
            }
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isEnabled()) {
                int newTheme = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                AppCompatDelegate.setDefaultNightMode(newTheme);
                sharedPrefManager.saveTheme(newTheme);
            }
        });

        // Other listeners for change password, logout...
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
