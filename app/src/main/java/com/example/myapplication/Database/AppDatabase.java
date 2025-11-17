package com.example.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Entity.UserEntity;

// CORRECTED: Incremented the version number from 2 to 3
@Database(entities = {UserEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_db")
                    .allowMainThreadQueries() // ⚠️ For demo purposes only
                    .fallbackToDestructiveMigration() // This will delete old data on version change
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
}
