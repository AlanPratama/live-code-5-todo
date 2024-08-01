package com.liveCodeTodoList.liveCodeTodo.service;

import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationRefreshTokenResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.AuthenticationResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    UserDTO register(RegisterRequestDTO request);
    AuthenticationResponseDTO login(AuthenticationRequestDTO request);
    AuthenticationRefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
