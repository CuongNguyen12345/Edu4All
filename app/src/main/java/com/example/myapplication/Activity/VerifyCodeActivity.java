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

import com.example.myapplication.R;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText etEmail, etCode;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_code);

        init();
        verifyOTP();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void verifyOTP() {
        Intent intent = getIntent();
        String OTP = intent.getStringExtra("otp");
        String email = intent.getStringExtra("email");
        etEmail.setText(email);

        btnConfirm.setOnClickListener(v -> handleConfirm(email, OTP));
    }


    private void handleConfirm(String email, String OTP) {
        if(OTP.equals(etCode.getText().toString())) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Mã xác nhận sai", LENGTH_SHORT).show();
        }
    }

    private void init() {
        etCode = findViewById(R.id.etCode);
        etEmail = findViewById(R.id.etEmail);
        btnConfirm = findViewById(R.id.btnConfirm);
    }
}