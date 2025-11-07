package com.example.myapplication.Component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;


public class CustomBottomNav extends LinearLayout {
    public CustomBottomNav(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_bottom_nav, this);
        initListeners(context);
    }

    private void initListeners(Context context) {
        findViewById(R.id.nav_home).setOnClickListener(v ->
                Toast.makeText(context, "Trang chủ", Toast.LENGTH_SHORT).show());

        findViewById(R.id.nav_search).setOnClickListener(v ->
                Toast.makeText(context, "Tìm kiếm bài học", Toast.LENGTH_SHORT).show());

        findViewById(R.id.nav_save).setOnClickListener(v ->
                Toast.makeText(context, "Lưu bài học", Toast.LENGTH_SHORT).show());

        findViewById(R.id.nav_settings).setOnClickListener(v ->
                Toast.makeText(context, "Cài đặt", Toast.LENGTH_SHORT).show());
    }
}
