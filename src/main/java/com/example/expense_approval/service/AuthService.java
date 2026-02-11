package com.example.expense_approval.service;

import com.example.expense_approval.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(String username, String password);
}
