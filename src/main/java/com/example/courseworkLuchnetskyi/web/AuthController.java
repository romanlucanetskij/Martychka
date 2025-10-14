package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.AuthResponse;
import com.example.courseworkLuchnetskyi.dto.LoginRequest;
import com.example.courseworkLuchnetskyi.dto.RegisterRequest;
import com.example.courseworkLuchnetskyi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
