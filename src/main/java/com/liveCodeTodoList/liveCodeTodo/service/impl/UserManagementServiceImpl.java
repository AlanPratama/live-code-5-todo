package com.liveCodeTodoList.liveCodeTodo.service.impl;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import com.liveCodeTodoList.liveCodeTodo.repository.UserRepository;
import com.liveCodeTodoList.liveCodeTodo.service.UserManagementService;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.NotFoundException;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.ChangeRoleUserRequest;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.UserManagementDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserManagementDTO> getAll(Pageable pageable) {
        Page<User> tempUser = userRepository.findAll(pageable);
        List<UserManagementDTO> userManagementDTOS = tempUser.stream()
                .map(MapUserDTO::MapToUserManagementDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(userManagementDTOS, pageable, tempUser.getNumberOfElements());
    }


    @Override
    public UserManagementDTO getOne(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));

        return MapUserDTO.MapToUserManagementDTO(user);
    }


    // SUPER ADMIN ROLE
    @Override
    public UserManagementDTO changeRole(ChangeRoleUserRequest request, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        user.setRole(request.getRole());

        return MapUserDTO.MapToUserManagementDTO(userRepository.save(user));
    }

    @Override
    public UserManagementDTO createSuperAdmin(RegisterRequestDTO request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.SUPER_ADMIN)
                .createdAt(LocalDate.now())
                .build();

        return MapUserDTO.MapToUserManagementDTO(userRepository.save(user));
    }
}
