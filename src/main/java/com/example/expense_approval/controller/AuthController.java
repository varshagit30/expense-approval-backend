package com.example.expense_approval.controller;

import com.example.expense_approval.dto.requestDto.LoginRequest;
import com.example.expense_approval.dto.LoginResponse;
import com.example.expense_approval.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token = authService.login(request.getUsername(), request.getPassword());

        LoginResponse res = new LoginResponse();
        res.setToken(token);
        return res;
    }
}
