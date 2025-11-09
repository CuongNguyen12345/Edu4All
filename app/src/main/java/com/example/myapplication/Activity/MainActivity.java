package com.example.myapplication.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Database.AppDatabase;
import com.example.myapplication.Entity.UserEntity;
import com.example.myapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassword;
    private Button btnLogin;
    private TextView tvForgot, tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        init();



        btnLogin.setOnClickListener(v -> {
            List<UserEntity> l = AppDatabase.getInstance(MainActivity.this).userDao().getAllUsers();
            String username = edtUserName.getText().toString();
            String password = edtPassword.getText().toString();
            UserEntity user = AppDatabase.getInstance(MainActivity.this).userDao().getUserByUsername(username);
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                Intent intent = new Intent(this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(this, "Đăng nhập thành công", LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", LENGTH_SHORT).show();
                return;
            }
        });



        tvSignup.setOnClickListener(v -> {
            handleClickSignup();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleClickSignup() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void init() {
        edtUserName = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgot = findViewById(R.id.tvForgot);
        tvSignup = findViewById(R.id.tvSignup);
    }
}