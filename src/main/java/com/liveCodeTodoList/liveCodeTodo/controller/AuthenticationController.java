package com.liveCodeTodoList.liveCodeTodo.controller;

import com.liveCodeTodoList.liveCodeTodo.service.AuthenticationService;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapErrors;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponseError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final AuthenticationService authenticatioService;

    @PostMapping("/register")
    @Validated
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Register Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
    }

    @PostMapping("/login")
    @Validated
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Login Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.refreshToken(request, response));
    }
}
