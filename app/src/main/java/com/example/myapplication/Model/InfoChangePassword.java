package com.example.myapplication.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoChangePassword {
    private String email;
    private String newPassword;
    private String confirmPassword;

    public InfoChangePassword(String email, String newPassword, String confirmPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}
