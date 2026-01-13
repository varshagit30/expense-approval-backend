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

        LoginResponse res = new LoginResponse();

        try {
            String token = authService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            res.setToken(token);
            res.setMessage("Login successful");
            return ResponseEntity.ok(res);

        } catch (RuntimeException ex) {
            res.setToken(null);
            res.setMessage(ex.getMessage());

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(res);
        }
    }
}
