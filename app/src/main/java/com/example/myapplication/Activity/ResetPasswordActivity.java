package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResetPasswordActivity extends AppCompatActivity {
    private final String senderEmail = "nguyencuong11112004@gmail.com";
    private final String appPassword = "fgcodsgcboyedlbz";
    private Button btnCancel, btnConfirm;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        init();

        btnCancel.setOnClickListener(v -> {
            finish();
        });

        btnConfirm.setOnClickListener(v -> {
            try {
                handleClickConfirm();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private String generateOTP() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    private void sendMail(String userEmail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        String otp = generateOTP();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        message.setSubject("Mã xác nhận đổi mật khẩu");
        message.setText("Mã OTP của bạn là: " + otp);

        Transport.send(message);
    }

    private void handleClickConfirm() throws MessagingException {
        String email = etEmail.getText().toString().trim();
        if(!email.isEmpty()) {
            new Thread(() -> {
                try {
                    sendMail(email);
                    runOnUiThread(() -> {
                        Intent intent = new Intent(ResetPasswordActivity.this, VerifyCodeActivity.class);
                        startActivity(intent);
                    });
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
    private void init() {
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        etEmail = findViewById(R.id.etEmail);
    }
}