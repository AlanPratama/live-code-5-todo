package com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserManagementDTO {
    private Integer id;
    private String username;
    private String email;
    private UserRole role;
    private LocalDate createdAt;
}
