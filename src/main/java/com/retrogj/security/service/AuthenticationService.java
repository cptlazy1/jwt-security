package com.retrogj.security.service;

import com.retrogj.security.dto.AuthenticationRequest;
import com.retrogj.security.dto.AuthenticationResponse;
import com.retrogj.security.dto.RegisterRequest;
import com.retrogj.security.model.Role;
import com.retrogj.security.model.User;
import com.retrogj.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    // Original register method with JWT
//    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        var user = User
//                .builder()
//                .username(registerRequest.getUsername())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .email(registerRequest.getEmail())
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        var jwToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .jwToken(jwToken)
//                .build();
//    }

    // Alternative register method without JWT
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User
                .builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .profileIsPrivate(registerRequest.getProfileIsPrivate())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwToken(jwToken)
                .build();
    }
}
