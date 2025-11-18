package com.example.myapplication.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Database.AppDatabase;
import com.example.myapplication.Entity.UserEntity;
import com.example.myapplication.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etChangeEmail, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        UserDao dao = AppDatabase.getInstance(this).userDao();

        init();
        setupChangePassword(dao);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupChangePassword(UserDao dao) {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        etChangeEmail.setText(email);

        btnChangePassword.setOnClickListener(v -> handleChangePassword(email, dao));

    }

    private void handleChangePassword(String email, UserDao dao) {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        UserEntity entity = dao.getUserByEmailAddress(email);
        if(newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", LENGTH_SHORT).show();
            return;
        }

        if(newPassword.equals(confirmPassword)) {
            entity.setPassword(newPassword);
            dao.updateUser(entity);
            Toast.makeText(this, "Đổi mật khẩu thành công", LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "Mật khẩu lặp lại không đúng", LENGTH_SHORT).show();
        }
    }

    private void init() {
        etChangeEmail = findViewById(R.id.etChangeEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
    }
}