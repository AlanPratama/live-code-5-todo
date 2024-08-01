package com.liveCodeTodoList.liveCodeTodo.controller;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.service.TodoItemManagement;
import com.liveCodeTodoList.liveCodeTodo.utils.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/todos")
public class AdminTodoController {
    private final TodoItemManagement todoItemManagement;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false)String sortBy,
            @RequestParam(required = false)String order
    ) {
        return ResponseEntity.ok(new PaginationResponse<>(todoItemManagement.getAll(pageable, status, sortBy, order)));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(todoItemManagement.getOne(id));
    }


}
