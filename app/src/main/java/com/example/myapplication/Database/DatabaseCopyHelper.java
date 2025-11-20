package com.example.myapplication.Database;

import android.content.Context;
import java.io.*;

public class DatabaseCopyHelper {

    private Context context;
    private static final String DB_NAME = "quiz.db";
    private String DB_PATH;

    public DatabaseCopyHelper(Context context) {
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    public void createDatabase() throws IOException {
        File dbFile = new File(DB_PATH);

        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
            copyDatabase();
        } else {
            // Nếu tệp đã tồn tại, hãy xóa và sao chép lại
            dbFile.delete();
            copyDatabase();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(DB_PATH);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }
}
