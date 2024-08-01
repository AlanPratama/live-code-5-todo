package com.liveCodeTodoList.liveCodeTodo.controller;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import com.liveCodeTodoList.liveCodeTodo.service.UserManagementService;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.ValidateException;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.authDTO.RegisterRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO.ChangeRoleUserRequest;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapErrors;
import com.liveCodeTodoList.liveCodeTodo.utils.response.PaginationResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class UserManagementController {
    private final UserManagementService userManagementService;
    private final String SECRET_KEY = "superman admin turing machine gigachad sigma";
    private final String ADMIN_SECRET_KEY = "admin turing machine alpha beta";

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(new PaginationResponse<>(userManagementService.getAll(pageable)));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(userManagementService.getOne(id));
    }

    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Validated
    public ResponseEntity<?> changeStatus(
            @Valid @RequestBody ChangeRoleUserRequest request,
            Errors errors,
            @PathVariable Integer id,
            @RequestHeader(value = "X-Admin-Secret-Key", required = true) String secretKey
    ){
        if (!ADMIN_SECRET_KEY.equals(secretKey)) throw new ValidateException("Invalid Secret Key!");

        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Create Todo Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.ok(userManagementService.changeRole(request, id));
    }

    @PostMapping("/super-admin")
    @Validated
    public ResponseEntity<?> createSuperAdmin(
            @Valid @RequestBody RegisterRequestDTO request,
            Errors errors,
            @RequestHeader(value = "X-Super-Admin-Secret-Key", required = true) String secretKey
    ) {
        if (!SECRET_KEY.equals(secretKey)) throw new ValidateException("Invalid Secret Key!");

        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Create Todo Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userManagementService.createSuperAdmin(request));
    }

}
