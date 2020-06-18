package com.course.pontointeligente.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {
    private String email;
    private String password;

    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password can't be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
