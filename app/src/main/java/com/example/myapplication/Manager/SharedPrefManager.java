package com.example.myapplication.Manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "Edu4AllUserStats";
    private static final String KEY_XP = "user_xp";
    private static final String KEY_LEVEL = "user_level";
    private static final String KEY_USERNAME = "user_username";
    private static final String KEY_THEME = "user_theme"; // Added for theme

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserStats(int xp, int level) {
        editor.putInt(KEY_XP, xp);
        editor.putInt(KEY_LEVEL, level);
        editor.apply();
    }

    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void saveTheme(int themeMode) {
        editor.putInt(KEY_THEME, themeMode);
        editor.apply();
    }

    public int getXp() {
        return sharedPreferences.getInt(KEY_XP, 0);
    }

    public int getLevel() {
        return sharedPreferences.getInt(KEY_LEVEL, 1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public int getTheme() {
        // Default to -1 (system default)
        return sharedPreferences.getInt(KEY_THEME, -1);
    }
}
