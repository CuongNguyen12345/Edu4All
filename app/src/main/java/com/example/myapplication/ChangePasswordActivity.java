package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Database.AppDatabase;
import com.example.myapplication.Entity.UserEntity;
import com.example.myapplication.Manager.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private Button btnSaveChanges;
    private AppDatabase appDatabase;
    private UserEntity currentUser;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        appDatabase = AppDatabase.getInstance(this);
        sharedPrefManager = new SharedPrefManager(this);

        initializeViews();
        loadCurrentUser();

        btnSaveChanges.setOnClickListener(v -> handleChangePassword());
    }

    private void initializeViews() {
        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
    }

    private void loadCurrentUser() {
        String username = sharedPrefManager.getUsername();
        if (username != null) {
            currentUser = appDatabase.userDao().getUserByUsername(username);
        } else {
            // This case should ideally not happen if the user is logged in.
            Toast.makeText(this, "Lỗi: Không thể xác thực người dùng!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void handleChangePassword() {
        String currentPassword = edtCurrentPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser == null) {
            Toast.makeText(this, "Lỗi: Không thể xác thực người dùng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the current password is correct
        if (!currentUser.getPassword().equals(currentPassword)) {
            Toast.makeText(this, "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the password in the database
        currentUser.setPassword(newPassword);
        appDatabase.userDao().updateUser(currentUser);

        Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        finish(); // Go back to the settings screen
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
