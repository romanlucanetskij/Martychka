package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.AuthResponse;
import com.example.courseworkLuchnetskyi.dto.LoginRequest;
import com.example.courseworkLuchnetskyi.dto.RegisterRequest;
import com.example.courseworkLuchnetskyi.model.UserAccount;
import com.example.courseworkLuchnetskyi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        UserAccount user = userAccountService.register(request);
        UserDetails userDetails = userAccountService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = userAccountService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
