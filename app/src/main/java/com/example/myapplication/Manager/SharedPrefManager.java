package com.example.myapplication.Manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "Edu4AllUserStats";
    private static final String KEY_XP = "user_xp";
    private static final String KEY_LEVEL = "user_level";

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

    public int getXp() {
        return sharedPreferences.getInt(KEY_XP, 0); // Default to 0 if not found
    }

    public int getLevel() {
        return sharedPreferences.getInt(KEY_LEVEL, 1); // Default to 1 if not found
    }
}
