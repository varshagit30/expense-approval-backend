package com.example.expense_approval.controller;

import com.example.expense_approval.dto.requestDto.LoginRequest;
import com.example.expense_approval.dto.LoginResponse;
import com.example.expense_approval.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(response);
    }
}
