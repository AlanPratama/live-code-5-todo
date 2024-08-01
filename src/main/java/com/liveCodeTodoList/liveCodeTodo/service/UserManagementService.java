package com.liveCodeTodoList.liveCodeTodo.service;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import com.liveCodeTodoList.liveCodeTodo.repository.UserRepository;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.ChangeRoleUserRequest;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserManagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {

    Page<UserManagementDTO> getAll(Pageable pageable);
    UserManagementDTO getOne(Integer id);

    // SUPER ADMIN ROLE
    UserManagementDTO changeRole(ChangeRoleUserRequest request, Integer id);

    UserManagementDTO createSuperAdmin(RegisterRequestDTO request);
}
