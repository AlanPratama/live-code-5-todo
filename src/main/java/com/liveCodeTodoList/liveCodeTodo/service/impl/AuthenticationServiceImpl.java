package com.liveCodeTodoList.liveCodeTodo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liveCodeTodoList.liveCodeTodo.configuration.JwtService;
import com.liveCodeTodoList.liveCodeTodo.entity.enums.TokenType;
import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.Token;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import com.liveCodeTodoList.liveCodeTodo.repository.TokenRepository;
import com.liveCodeTodoList.liveCodeTodo.repository.UserRepository;
import com.liveCodeTodoList.liveCodeTodo.service.AuthenticationService;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.ValidateException;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationRefreshTokenResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserToken.isEmpty()) return;

        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }


    @Override
    public UserDTO register(RegisterRequestDTO request) {
        Optional<User> isUserEmail = userRepository.findByEmail(request.getEmail());
        Optional<User> isUserUsername = userRepository.findByUsername(request.getUsername());

        if (isUserEmail.isPresent()) throw new ValidateException("User With Email: " + request.getEmail() + " Already Exist!");
        if (isUserUsername.isPresent()) throw new ValidateException("User With Username: " + request.getEmail() + " Already Exist!");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .createdAt(LocalDate.now())
                .build();

        User createdUser = userRepository.save(user);

        return MapUserDTO.MapToUserDTO(createdUser);
    }

    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public AuthenticationRefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new ValidateException("Invalid Token!");

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsrname(refreshToken);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                return AuthenticationRefreshTokenResponse.builder()
                        .accessToken(accessToken)
                        .build();
            }
        }

        throw new ValidateException("Invalid Token!");
    }
}
