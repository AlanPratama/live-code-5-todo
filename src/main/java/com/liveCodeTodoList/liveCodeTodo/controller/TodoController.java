package com.liveCodeTodoList.liveCodeTodo.controller;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.service.TodoService;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoStatusUpdateRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoUpdateRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapErrors;
import com.liveCodeTodoList.liveCodeTodo.utils.response.PaginationResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody TodoRequestDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Create Todo Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false)TaskStatus status,
            @RequestParam(required = false)String sortBy,
            @RequestParam(required = false)String order
    ) {
        return ResponseEntity.ok(new PaginationResponse<>(todoService.getAll(pageable, status, sortBy, order)));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getOne(id));
    }


    @PutMapping("{id}")
    @Validated
    public ResponseEntity<?> update(
            @Valid @RequestBody TodoUpdateRequestDTO request,
            Errors errors,
            @PathVariable Integer id
    ) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Create Todo Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.OK).body(todoService.update(request, id));
    }


    @PatchMapping("{id}/status")
    @Validated
    public ResponseEntity<?> updateStatus(
            @Valid @RequestBody TodoStatusUpdateRequestDTO request,
            Errors errors,
            @PathVariable Integer id
    ) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = MapErrors.renderErrors("Create Todo Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateStatus(request, id));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        todoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
