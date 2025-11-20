package com.example.myapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Model.Question2;
import com.example.myapplication.Model.Reading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quiz.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);

        // Copy DB từ assets vào /data/data/<app>/
        DatabaseCopyHelper helper = new DatabaseCopyHelper(context);
        try {
            helper.createDatabase();
        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo CSDL từ assets!", e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    // ============================================================
    // 1) READING – lấy bài đọc theo unit
    // ============================================================
    public Reading getReading(int unit_id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id, unit_id, title, content FROM reading_passages WHERE unit_id = ?",
                new String[]{String.valueOf(unit_id)}
        );

        if (c.moveToFirst()) {
            Reading r = new Reading(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3)
            );
            c.close();
            return r;
        }

        c.close();
        return null;
    }


    // ============================================================
    // 2) QUESTIONS – lấy danh sách câu hỏi theo unit
    // ============================================================
    public List<Question2> getQuestions(int unit_id) {
        List<Question2> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id, unit_id, question, optionA, optionB, optionC, optionD, correct " +
                        "FROM questions WHERE unit_id = ?",
                new String[]{String.valueOf(unit_id)}
        );

        while (c.moveToNext()) {
            list.add(new Question2(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7)
            ));
        }

        c.close();
        return list;
    }


    // ============================================================
    // 3) VOCABULARY – lấy danh sách từ vựng đầy đủ theo unit
    // ============================================================
    public List<String> getVocabularyList(int unit_id) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT word, meaning, example FROM vocabulary WHERE unit_id = ?",
                new String[]{String.valueOf(unit_id)}
        );

        while (c.moveToNext()) {
            String word = c.getString(0);
            String meaning = c.getString(1);
            String example = c.getString(2);

            String item = word + " – " + meaning;
            if (example != null && !example.trim().isEmpty()) {
                item += "\nVD: " + example;
            }

            list.add(item);
        }

        c.close();
        return list;
    }


    // ============================================================
    // 4) LISTENING – lấy SCRIPT_TEXT (vì CSDL của bạn dùng cột này)
    // ============================================================
    public String getListeningTranscript(int unit_id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT script_text FROM listening WHERE unit_id = ? LIMIT 1",
                new String[]{String.valueOf(unit_id)}
        );

        if (c.moveToFirst()) {
            String script = c.getString(0);
            c.close();
            return script;
        }

        c.close();
        return null;
    }


    // ============================================================
    // 5) LISTENING AUDIO FILE
    // ============================================================
    public String getListeningAudio(int unit_id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT audio_file FROM listening WHERE unit_id = ? LIMIT 1",
                new String[]{String.valueOf(unit_id)}
        );

        if (c.moveToFirst()) {
            String file = c.getString(0);
            c.close();
            return file;
        }

        c.close();
        return null;
    }


}
