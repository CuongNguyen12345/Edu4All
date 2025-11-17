package com.example.myapplication.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.AppDatabase;
import com.example.myapplication.Entity.UserEntity;
import com.example.myapplication.Manager.SharedPrefManager;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassword;
    private Button btnLogin;
    private TextView tvForgot, tvSignup;
    private LinearLayout btnGoogleLogin, btnFacebookLogin;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        edtUserName = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgot = findViewById(R.id.tvForgot);
        tvSignup = findViewById(R.id.tvSignup);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        // ... other listeners
    }

    private void handleLogin() {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", LENGTH_SHORT).show();
            return;
        }

        UserEntity user = AppDatabase.getInstance(this).userDao().getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Toast.makeText(this, "Đăng nhập thành công", LENGTH_SHORT).show();

            // CORRECTED: Save username to SharedPreferences on successful login
            sharedPrefManager.saveUsername(user.getUsername());

            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", LENGTH_SHORT).show();
        }
    }

    // ... other methods
}
