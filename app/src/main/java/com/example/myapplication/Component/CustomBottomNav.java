package com.example.myapplication.Component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.Activity.HomeActivity;
import com.example.myapplication.Activity.SavedLessonsActivity;
import com.example.myapplication.Activity.SearchActivity;
import com.example.myapplication.Activity.SettingsActivity;
import com.example.myapplication.R;

public class CustomBottomNav extends LinearLayout {

    public CustomBottomNav(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_bottom_nav, this, true);

        View navHome = findViewById(R.id.nav_home);
        View navSearch = findViewById(R.id.nav_search);
        View navSave = findViewById(R.id.nav_save);
        View navSettings = findViewById(R.id.nav_settings);

        navHome.setOnClickListener(v -> navigateTo(HomeActivity.class, true));
        navSearch.setOnClickListener(v -> navigateTo(SearchActivity.class, false));
        navSave.setOnClickListener(v -> navigateTo(SavedLessonsActivity.class, false));
        navSettings.setOnClickListener(v -> navigateTo(SettingsActivity.class, false));
    }

    private void navigateTo(Class<?> destinationActivity, boolean clearTop) {
        Context context = getContext();
        // Avoid navigating to the same activity
        if (context.getClass() == destinationActivity) {
            return;
        }

        Intent intent = new Intent(context, destinationActivity);
        if (clearTop) {
            // Clears the activity stack and brings Home to the top
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);
    }
}
