package com.liveCodeTodoList.liveCodeTodo.utils.mapper;

import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserManagementDTO;

public class MapUserDTO {

    public static UserDTO MapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }


    public static UserManagementDTO MapToUserManagementDTO(User user) {
        return UserManagementDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
