package com.example.expense_approval.service.sericeImpl;

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
    public String login(String username, String password) {

        UserDao user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            log.warn("Login failed: wrong password for -> {}", username);
            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }
}
