package com.example.expense_approval.service.sericeImpl;

import com.example.expense_approval.dto.LoginResponse;
import com.example.expense_approval.entity.UserDao;
import com.example.expense_approval.repository.UserRepository;
import com.example.expense_approval.security.JwtService;
import com.example.expense_approval.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(String username, String password) {

        UserDao user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            log.warn("Login failed: wrong password for -> {}", username);
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        LoginResponse res = new LoginResponse();
        res.setToken(token);
        res.setUserId(user.getId());

        return res;
    }
}
