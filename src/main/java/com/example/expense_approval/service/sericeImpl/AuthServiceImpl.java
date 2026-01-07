package com.example.expense_approval.service.sericeImpl;

import com.example.expense_approval.entity.UserDao;
import com.example.expense_approval.repository.UserRepository;
import com.example.expense_approval.security.JwtService;
import com.example.expense_approval.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public String login(String username, String password) {

        UserDao user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }
}
